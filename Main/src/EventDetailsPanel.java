import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class EventDetailsPanel extends JPanel {
    private JTextArea eventTextArea;
    private CalendarExample calendarExample; // Reference to the CalendarExample instance

    public EventDetailsPanel(CalendarExample calendarExample) {
        this.calendarExample = calendarExample;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 100)); // Set preferred size for the panel

        JLabel titleLabel = new JLabel("Event Details");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        eventTextArea = new JTextArea();
        eventTextArea.setEditable(false); // Make the text area read-only
        JScrollPane scrollPane = new JScrollPane(eventTextArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Method to update the event list based on the selected date
    public void updateEventList(LocalDate date) {
        List<String> events = calendarExample.getEventsForDate(date); // Retrieve events for the date
        if (events != null && !events.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String event : events) {
                sb.append("- ").append(event).append("\n");
            }
            eventTextArea.setText(sb.toString());
        } else {
            eventTextArea.setText("No events for this date");
        }
    }
}
