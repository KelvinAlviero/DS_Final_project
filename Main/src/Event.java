import java.time.LocalDate;

public class Event {
    private String name;
    private LocalDate date;
    private String time;
    private String location;

    public Event(String name, LocalDate date, String time, String location) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
    }

    // Getters and setters
    // ...
}
