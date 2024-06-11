import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CalendarExample extends JFrame {
    private LinkedList<List<Color>> themes;
    private LinkedList<ImageIcon> Icons;
    private static final int WIDTH = 1200; // Width
    private static final int HEIGHT = 600; // Height
    private BoxPanel boxPanel;
    private int currentThemeIndex;
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 80;
    private LocalDate currentDate;
    private JPanel calendarPanel;
    private JLabel monthLabel;

    public CalendarExample() {
        setTitle("Mark-2C 'Calendar', Mod. 2024");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        themes = new LinkedList<>(); // Theme palettes
        // Retro colors (Background, Box 1, Box 2, Box 3, Box 4)
        themes.add(Arrays.asList(new Color(227, 174, 87), new Color(243, 208, 150), new Color(243, 208, 150), new Color(182, 112, 0), new Color(182, 112, 0)));
        // Light mode
        themes.add(Arrays.asList(new Color(213, 212, 210), new Color(38, 43, 44), new Color(38, 43, 44), new Color(236, 0, 0), new Color(236, 0, 0)));
        // Dark mode
        themes.add(Arrays.asList(new Color(43, 49, 51), new Color(254, 246, 219), new Color(254, 246, 219), new Color(232, 198, 117), new Color(232, 198, 117)));
        currentThemeIndex = 0;

        // Load the image icon
        URL imgURL = getClass().getResource("/ChangeIcon.png");
        ImageIcon icon = new ImageIcon(imgURL);//THIS ERROR DOESN'T MATTER
        //scaling it down
        Image scaledImage = icon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        //Placing and sizing the icon
        JLabel imageButton = new JLabel(scaledIcon);
        imageButton.setBounds(WIDTH - 100, 10, BUTTON_WIDTH, BUTTON_HEIGHT); // (x, y, width, height)
        imageButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        imageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateTheme();
            }
        });

        // Create the drawing panel
        boxPanel = new BoxPanel();
        boxPanel.setBounds(0, 0, WIDTH, HEIGHT);

        // Create the layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        layeredPane.add(boxPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(imageButton, JLayeredPane.PALETTE_LAYER);

        // Initialize calendar
        initCalendar(layeredPane);

        add(layeredPane);
        validate();
    }

    // Updates theme by adding +1
    private void updateTheme() {
        currentThemeIndex = (currentThemeIndex + 1) % themes.size();
        boxPanel.repaint();
    }

    private void initCalendar(JLayeredPane layeredPane) {
        JPanel calendarContainer = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(Color.BLACK);
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                g2d.dispose();
            }
        };
        calendarContainer.setPreferredSize(new Dimension(800, 600)); // Fixed size for the calendar panel
        calendarContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        currentDate = LocalDate.now();

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        JButton prevButton = new JButton("<");
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDate = currentDate.minusMonths(1);
                updateCalendar();
            }
        });
        JButton nextButton = new JButton(">");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDate = currentDate.plusMonths(1);
                updateCalendar();
            }
        });
        monthLabel = new JLabel(currentDate.getMonth().toString() + " " + currentDate.getYear());
        monthLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(monthLabel, BorderLayout.CENTER);
        headerPanel.add(nextButton, BorderLayout.EAST);

        calendarPanel = new CustomPanel(); // Use CustomPanel instead of JPanel
        calendarPanel.setLayout(new GridLayout(0, 7, 5, 5));
        calendarPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : daysOfWeek) {
            calendarPanel.add(new JLabel(day, SwingConstants.CENTER));
        }

        updateCalendar();

        calendarContainer.add(headerPanel, BorderLayout.NORTH);
        calendarContainer.add(calendarPanel, BorderLayout.CENTER);

        // Add calendar container to the layered pane
        layeredPane.add(calendarContainer, JLayeredPane.MODAL_LAYER);
    }

    private void updateCalendar() {
        calendarPanel.removeAll(); // Remove previous day labels

        int daysInMonth = currentDate.lengthOfMonth();
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        DayOfWeek firstDayOfWeek = firstDayOfMonth.getDayOfWeek();

        // Add empty labels for days before the first day of the month
        for (int i = 0; i < firstDayOfWeek.getValue() % 7; i++) {
            calendarPanel.add(new JLabel(""));
        }

        // Add labels for each day of the month
        for (int day = 1; day <= daysInMonth; day++) {
            JLabel label = new JLabel(Integer.toString(day), SwingConstants.CENTER);
            calendarPanel.add(label);
        }

        // Update the month label
        monthLabel.setText(currentDate.getMonth().toString() + " " + currentDate.getYear());

        revalidate(); // Revalidate the container
        repaint(); // Repaint the container
    }

    private class BoxPanel extends JPanel {
        @Override
        // Box painter
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (!themes.isEmpty()) {
                // Get the current theme
                List<Color> currentTheme = themes.get(currentThemeIndex);

                // Set the background color
                setBackground(currentTheme.get(0));

                // Calendar
                g.setColor(currentTheme.get(1));
                g.fillRect(50, 50, 800, 400); // (x, y, width, height)

                // Notifications
                g.setColor(currentTheme.get(2));
                g.fillRect(900, 50, 200, 400); // (x, y, width, height)

                // Calendar extras
                g.setColor(currentTheme.get(3));
                g.fillRect(50, 50, 800, 70); // (x, y, width, height)

                // Notifications extras
                g.setColor(currentTheme.get(4));
                g.fillRect(900, 50, 200, 70); // (x, y, width, height)
            }
        }
    }

    // CustomPanel class for drawing additional shapes
    private class CustomPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw additional shapes or customizations here
            // For example, you can draw rectangles, circles, lines, etc.
            // Use the Graphics object (g) to draw shapes
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalendarExample().setVisible(true);
            }
        });
    }
}
