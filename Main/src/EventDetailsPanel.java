import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class EventDetailsPanel extends JPanel {
    private JTextArea eventTextArea;
    private Calendar calendar;

    public EventDetailsPanel(Calendar calendar) {
        this.calendar = calendar;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 100));

        JLabel titleLabel = new JLabel("Event Details");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        eventTextArea = new JTextArea();
        eventTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(eventTextArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Highlights the date depending on the day
    public void updateEventList(LocalDate date) {
        List<String> events = calendar.getEventsForDate(date); // Retrieve events for the date
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
