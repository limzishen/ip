package task;
import java.time.LocalDateTime;

public class Event extends Task {
    public LocalDateTime startTime;
    public LocalDateTime endTime;

    /**
     * Constructs an Event with a specified name, status, start time, and end time.
     * * @param taskName  The name or description of the event.
     * @param status    The completion status of the event.
     * @param startTime The {@link LocalDateTime} representing the start of the event.
     * @param endTime   The {@link LocalDateTime} representing the end of the event.
     */
    public Event (String taskName, Boolean status, LocalDateTime startTime, LocalDateTime endTime) {
        super(taskName, status);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructs an Event with a specified name, start time, and end time.
     * The status is initialized to false (not done) by default.
     *
     * @param taskName  The name or description of the event.
     * @param startTime The {@link LocalDateTime} representing the start of the event.
     * @param endTime   The {@link LocalDateTime} representing the end of the event.
     */
    public Event(String taskName, LocalDateTime startTime, LocalDateTime endTime) {
        super(taskName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Formats the event into a string suitable for file storage.
     * Uses the "EVENT | status | taskName | startTime | endTime" format.
     * Dates are formatted using the {@code saveFormat} defined in the parent {@link Task} class.
     *
     * @return A pipe-delimited string representing the event for storage.
     */
    @Override
    public String storeTask() {
        return String.format("EVENT | %s | %s | %s | %s", status.toString(), super.taskName, startTime.format(super.saveFormat), endTime.format(super.saveFormat));
    }

    /**
     * Returns a string representation of the event for console display.
     * Includes the [E] tag, status icon, and formatted start and end times.
     *
     * @return A user-friendly string representation of the event.
     */
    @Override
    public String toString() {
        String statusIcon = status ? "X" : " "; // X if true, space if false
        return String.format("[E] [%s] %s Start Time: %s End Time: %s",
                statusIcon, taskName, startTime.format(super.saveFormat), endTime.format(super.saveFormat));
    }

}
