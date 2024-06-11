import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestImageButton extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 80;
    private JLabel imageButton;
    private List<ImageIcon> icons;
    private int currentIconIndex;

    public TestImageButton() {
        setTitle("Image Button Test");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Use absolute positioning

        icons = new ArrayList<>();
        icons.add(loadIcon("/ChangeIcon.png"));
        icons.add(loadIcon("/ChangeIcon2.png"));
        icons.add(loadIcon("/ChangeIcon3.png"));
        currentIconIndex = 0;

        if (!icons.isEmpty()) {
            imageButton = new JLabel(icons.get(currentIconIndex));
            imageButton.setBounds(WIDTH - 100, 10, BUTTON_WIDTH, BUTTON_HEIGHT); // (x, y, width, height)
            imageButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            imageButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    updateIcon();
                }
            });

            add(imageButton);
        } else {
            System.err.println("No icons were loaded.");
            JLabel errorLabel = new JLabel("No icons available");
            errorLabel.setBounds(WIDTH - 100, 10, 120, 80);
            add(errorLabel);
        }
    }

    private ImageIcon loadIcon(String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image scaledImage = icon.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.err.println("Could not find the image file: " + path);
            return null;
        }
    }

    private void updateIcon() {
        currentIconIndex = (currentIconIndex + 1) % icons.size();
        imageButton.setIcon(icons.get(currentIconIndex));
        System.out.println("Icon changed!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TestImageButton().setVisible(true);
            }
        });
    }
}
