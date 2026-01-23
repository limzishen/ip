package task;
import java.time.LocalDateTime;

public class Event extends Task {
    public LocalDateTime startTime;
    public LocalDateTime endTime;

    public Event (String taskName, Boolean status, LocalDateTime startTime, LocalDateTime endTime) {
        super(taskName, status);
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public Event(String taskName, LocalDateTime startTime, LocalDateTime endTime) {
        super(taskName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String storeTask() {
        return String.format("EVENT | %s | %s | %s | %s", status.toString(), super.taskName, startTime.format(super.saveFormat), endTime.format(super.saveFormat));
    }

    @Override
    public String toString() {
        String statusIcon = status ? "X" : " "; // X if true, space if false
        return String.format("[E] [%s] %s Start Time: %s End Time: %s",
                statusIcon, taskName, startTime.format(super.saveFormat), endTime.format(super.saveFormat));
    }

}
