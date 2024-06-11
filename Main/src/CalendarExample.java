import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CalendarExample extends JFrame {
    private LinkedList<List<Color>> themes;
    private static final int WIDTH = 1200; // Width
    private static final int HEIGHT = 600; // Height
    private int currentThemeIndex;
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 80;
    private CustomCalendarPanel calendarPanel;
    private JLabel monthLabel;

    public CalendarExample() {
        setTitle("Mark-3C 'Calendar', Mod. 2024");
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

        // Initialize month label
        monthLabel = new JLabel(getFormattedMonthYear(LocalDate.now()));
        monthLabel.setHorizontalAlignment(JLabel.CENTER);
        monthLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Initialize calendar
        calendarPanel = new CustomCalendarPanel();

        // Create a split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, createRandomRectanglePanel(), createCalendarPanel());
        splitPane.setResizeWeight(0.5); // Set initial resize weight to split the pane in half

        add(splitPane);
        validate();
    }

    // Updates theme by adding +1
    private void updateTheme() {
        currentThemeIndex = (currentThemeIndex + 1) % themes.size();
        calendarPanel.repaint();
    }

    private JPanel createRandomRectanglePanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw a random colored rectangle
                g.setColor(new Color(227, 174, 87));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    private JPanel createCalendarPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(monthLabel, BorderLayout.NORTH);
        panel.add(calendarPanel, BorderLayout.CENTER);
        return panel;
    }

    private String getFormattedMonthYear(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalendarExample().setVisible(true);
            }
        });
    }

    private class CustomCalendarPanel extends JPanel {
        // Implement calendar functionality
        private LocalDate currentDate;

        public CustomCalendarPanel() {
            currentDate = LocalDate.now();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawCalendar(g);
        }

        private void drawCalendar(Graphics g) {
            int width = getWidth();
            int height = getHeight();

            // Calculate cell width and height
            int cellWidth = width / 7;
            int cellHeight = height / 6;

            // Set font for drawing
            Font font = new Font("Arial", Font.PLAIN, 16);
            g.setFont(font);

            // Set starting date to the first day of the month
            LocalDate firstDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);

            // Get the day of the week of the first day of the month
            DayOfWeek firstDayOfWeek = firstDayOfMonth.getDayOfWeek();

            // Fill in the days of the month
            int day = 1;
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 7; col++) {
                    int x = col * cellWidth;
                    int y = (row + 1) * cellHeight;
                    if ((row == 0 && col >= firstDayOfWeek.getValue()) || (row > 0 && day <= currentDate.lengthOfMonth())) {
                        g.drawString(String.valueOf(day), x + (cellWidth / 2) - 6, y + (cellHeight / 2) + 6);
                        day++;
                    }
                }
            }
        }
    }
}
