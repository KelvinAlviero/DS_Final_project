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
    private Map<LocalDate, ArrayList<String>> eventArrayList;
    private Map<LocalDate, LinkedList<String>> eventLinkedList;

    public CustomCalendarPanel() {
        currentDate = LocalDate.now();
        this.events = new HashMap<>();
        this.eventArrayList = new HashMap<>();
        this.eventLinkedList = new HashMap<>();
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

        revalidate(); // Revalidates panel
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

        ArrayList<String> arrayList = eventArrayList.getOrDefault(date, new ArrayList<>());
        arrayList.add(event);
        eventArrayList.put(date, arrayList);

        LinkedList<String> linkedList = eventLinkedList.getOrDefault(date, new LinkedList<>());
        linkedList.add(event);
        eventLinkedList.put(date, linkedList);
    }

    // Method to remove an event from the calendar (Scrapped)
    public void removeEvent(LocalDate date, String event) {
        List<String> eventList = events.get(date);
        if (eventList != null) {
            eventList.remove(event);
            if (eventList.isEmpty()) {
                events.remove(date);
            }

            ArrayList<String> arrayList = eventArrayList.get(date);
            if (arrayList != null) {
                arrayList.remove(event);
                if (arrayList.isEmpty()) {
                    eventArrayList.remove(date);
                }
            }

            LinkedList<String> linkedList = eventLinkedList.get(date);
            if (linkedList != null) {
                linkedList.remove(event);
                if (linkedList.isEmpty()) {
                    eventLinkedList.remove(date);
                }
            }

            updateCalendar();
        }
    }

    // Method to get events for a specific date
    public List<String> getEventsForDate(LocalDate date) {
        return events.getOrDefault(date, Collections.emptyList());
    }

    // Method to clear the highlighted date (Also scrapped)
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

    // Method to compare performance of ArrayList and LinkedList
    public void comparePerformance(LocalDate date, String event) {
        long startTime, endTime, arrayListTime, linkedListTime;

        startTime = System.nanoTime();
        eventArrayList.getOrDefault(date, new ArrayList<>()).add(event);
        endTime = System.nanoTime();
        arrayListTime = endTime - startTime;

        startTime = System.nanoTime();
        eventLinkedList.getOrDefault(date, new LinkedList<>()).add(event);
        endTime = System.nanoTime();
        linkedListTime = endTime - startTime;

        System.out.println("ArrayList add time: " + arrayListTime + " ns");
        System.out.println("LinkedList add time: " + linkedListTime + " ns");
    }
}
