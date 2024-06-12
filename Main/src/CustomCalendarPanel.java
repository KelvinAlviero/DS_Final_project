import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.List;
import java.util.Locale;

public class CustomCalendarPanel extends JPanel {
    private LocalDate currentDate;
    private Map<LocalDate, JLabel> dateLabels;
    private LocalDate highlightedDate;
    private Map<LocalDate, List<String>> events;
    private JLabel monthLabel;

    public CustomCalendarPanel() {
        currentDate = LocalDate.now();
        this.events = new HashMap<>();
        dateLabels = new HashMap<>();
        highlightedDate = null;
        setLayout(new BorderLayout()); // Use BorderLayout

        // Add month label at the top
        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Serif", Font.BOLD, 20));
        add(monthLabel, BorderLayout.NORTH);

        // Create a panel for the calendar grid
        JPanel calendarGrid = new JPanel(new GridLayout(0, 7)); // GridLayout with 7 columns
        add(calendarGrid, BorderLayout.CENTER);

        updateCalendar();
    }

    // Method to update the calendar display
    public void updateCalendar() {
        // Update month label
        monthLabel.setText(currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentDate.getYear());

        // Get the panel containing the calendar grid
        JPanel calendarGrid = (JPanel) getComponent(1);

        calendarGrid.removeAll(); // Clear previous components
        dateLabels.clear(); // Clear previous date labels

        // Set starting date to the first day of the month
        LocalDate firstDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);

        // Get the day of the week of the first day of the month
        DayOfWeek firstDayOfWeek = firstDayOfMonth.getDayOfWeek();

        // Add labels for day names
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            JLabel dayLabel = new JLabel(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()), SwingConstants.CENTER);
            calendarGrid.add(dayLabel);
        }

        // Add empty labels for days before the first day of the month
        for (int i = 1; i < firstDayOfWeek.getValue(); i++) {
            calendarGrid.add(new JLabel());
        }

        // Fill in the days of the month
        for (int day = 1; day <= currentDate.lengthOfMonth(); day++) {
            LocalDate date = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), day);
            JLabel label = new JLabel(String.valueOf(day));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            dateLabels.put(date, label);
            calendarGrid.add(label);
        }

        revalidate(); // Revalidate the panel to reflect the changes
        repaint(); // Repaint the panel
    }

    // Getter for currentDate
    public LocalDate getCurrentDate() {
        return currentDate;
    }

    // Setter for currentDate
    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    // Method to highlight a specific date
    public void highlightDate(LocalDate date) {
        highlightedDate = date;
        JLabel label = dateLabels.get(date);
        if (label != null) {
            label.setOpaque(true);
            label.setBackground(Color.RED);
            label.setForeground(Color.WHITE);
        }
    }

    // Method to add an event to the calendar
    public void addEvent(LocalDate date, String event) {
        List<String> eventList = events.getOrDefault(date, new ArrayList<>());
        eventList.add(event);
        events.put(date, eventList);
    }

    // Method to get events for a specific date
    public List<String> getEventsForDate(LocalDate date) {
        return events.getOrDefault(date, Collections.emptyList());
    }

    // Method to clear the highlighted date
    public void clearHighlight() {
        if (highlightedDate != null) {
            JLabel label = dateLabels.get(highlightedDate);
            if (label != null) {
                label.setOpaque(false);
                label.setBackground(null);
                label.setForeground(Color.BLACK);
            }
            highlightedDate = null;
        }
    }
}
