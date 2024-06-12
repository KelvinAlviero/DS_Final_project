import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class CalendarExample extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;
    private CustomCalendarPanel calendarPanel;
    private EventDetailsPanel eventDetailsPanel;

    public CalendarExample() {
        setTitle("Custom Calendar Example");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize calendar panel
        calendarPanel = new CustomCalendarPanel();

        // Create event details panel
        eventDetailsPanel = new EventDetailsPanel(this);
        add(eventDetailsPanel, BorderLayout.NORTH);

        // Create a split pane with a random colored rectangle
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, createRandomRectanglePanel(), calendarPanel);
        splitPane.setResizeWeight(0.2);
        add(splitPane, BorderLayout.CENTER);

        // Create a button to highlight a specific date
        JButton highlightButton = new JButton("Highlight Date");
        highlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHighlightDialog();
            }
        });
        add(highlightButton, BorderLayout.SOUTH);
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

    private void showHighlightDialog() {
        // Create a dialog window for adding a widget
        JDialog dialog = new JDialog(this, "Add Widget", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);

        // Create panel for dialog components
        JPanel panel = new JPanel(new GridLayout(3, 2));

        // Date input
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField(LocalDate.now().toString());
        panel.add(dateLabel);
        panel.add(dateField);

        // Event input
        JLabel eventLabel = new JLabel("Event:");
        JTextField eventField = new JTextField();
        panel.add(eventLabel);
        panel.add(eventField);

        // Confirm button
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dateText = dateField.getText();
                String eventText = eventField.getText();
                LocalDate date = LocalDate.parse(dateText, DateTimeFormatter.ISO_DATE);
                addEvent(date, eventText);
                calendarPanel.highlightDate(date); // Highlight the date
                dialog.dispose();
            }
        });
        panel.add(confirmButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        panel.add(cancelButton);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void addEvent(LocalDate date, String event) {
        calendarPanel.addEvent(date, event); // Add event to calendar panel
        eventDetailsPanel.updateEventList(date); // Update event details panel
    }

    public List<String> getEventsForDate(LocalDate date) {
        if (calendarPanel != null) {
            return calendarPanel.getEventsForDate(date);
        } else {
            // Handle the case when calendarPanel is null
            return Collections.emptyList(); // Or handle differently as per your requirement
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CalendarExample calendarExample = new CalendarExample();
                calendarExample.setVisible(true);
            }
        });
    }
}
