import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main extends JFrame {
    private LinkedList<List<Color>> themes;
    private LinkedList<ImageIcon> Icons;
    private static final int WIDTH = 1200; // Width
    private static final int HEIGHT = 600; // Height
    private BoxPanel boxPanel;
    private int currentThemeIndex;
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 80;

    public Main() {
        setTitle("Mark-2C 'Calender', Mod. 2024");
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
        ImageIcon icon = new ImageIcon(imgURL);//THIS ERROR DOESN'T MATTERRRR
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

        add(layeredPane);
        validate();
    }

    // Updates theme by adding +1
    private void updateTheme() {
        currentThemeIndex = (currentThemeIndex + 1) % themes.size();
        boxPanel.repaint();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
