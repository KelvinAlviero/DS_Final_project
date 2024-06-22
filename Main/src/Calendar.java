import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class Calendar extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;
    private CustomCalendarPanel calendarPanel;
    private EventDetailsPanel eventDetailsPanel;

    public Calendar() {
        setTitle("Calender MK-4C");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize calendar panel
        calendarPanel = new CustomCalendarPanel();
        eventDetailsPanel = new EventDetailsPanel(this);

        // Add event details panel at the top
        add(eventDetailsPanel, BorderLayout.NORTH);

        // Create a panel for navigation buttons and add them to the top (Doesn't work sadly)
        JPanel navigationPanel = new JPanel();
        JButton prevButton = new JButton("<");
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendarPanel.setCurrentDate(calendarPanel.getCurrentDate().minusMonths(1));
                calendarPanel.updateCalendar();
            }
        });
        navigationPanel.add(prevButton);

        //Also doesnt work
        JButton nextButton = new JButton(">");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendarPanel.setCurrentDate(calendarPanel.getCurrentDate().plusMonths(1));
                calendarPanel.updateCalendar();
            }
        });
        navigationPanel.add(nextButton);

        add(navigationPanel, BorderLayout.SOUTH);

        //  Goofy rectangle that splits the array box and calender
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, createRandomRectanglePanel(), calendarPanel);
        splitPane.setResizeWeight(0.2);
        add(splitPane, BorderLayout.CENTER);

        // Create a button to highlight a specific date
        JButton highlightButton = new JButton("Add Event");
        highlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHighlightDialog();
            }
        });
        add(highlightButton, BorderLayout.SOUTH);
    }

    //The rectangle stuff (I thought multi colors would be cool but nah)
    private JPanel createRandomRectanglePanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draws the box
                g.setColor(new Color(227, 174, 87));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    //Popup window for entering stufff
    private void showHighlightDialog() {
        // Dialog for window
        JDialog dialog = new JDialog(this, "Add Event", true);
        dialog.setSize(300, 300);
        dialog.setLocationRelativeTo(this);

        // Create panel for dialog components
        JPanel panel = new JPanel(new GridLayout(4, 2));

        // Date input
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField(LocalDate.now().toString());
        panel.add(dateLabel);
        panel.add(dateField);

        // Time input
        JLabel timeLabel = new JLabel("Time (HH:MM):");
        JTextField timeField = new JTextField(LocalTime.now().toString());
        panel.add(timeLabel);
        panel.add(timeField);

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
                String timeText = timeField.getText();
                String eventText = eventField.getText();
                LocalDate date = LocalDate.parse(dateText, DateTimeFormatter.ISO_DATE);
                LocalTime time = LocalTime.parse(timeText, DateTimeFormatter.ISO_TIME);
                String event = timeText + " - " + eventText;
                addEvent(date, event);
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

    //Compares linked and array list
    private void addEvent(LocalDate date, String event) {
        calendarPanel.addEvent(date, event); // Add event to calendar panel
        calendarPanel.comparePerformance(date, event); // Compare performance
        eventDetailsPanel.updateEventList(date); // Update event details panel
    }


    public List<String> getEventsForDate(LocalDate date) {
        if (calendarPanel != null) {
            return calendarPanel.getEventsForDate(date);
        } else {
            return Collections.emptyList();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Calendar calendarExample = new Calendar();
                calendarExample.setVisible(true);
            }
        });
    }
}
