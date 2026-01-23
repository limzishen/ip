package Task;

import java.time.LocalDateTime;

public class Deadlines extends Task {
    LocalDateTime deadline;

    public Deadlines (String taskName, Boolean status, LocalDateTime deadline) {
        super(taskName, status);
        this.deadline = deadline;
    }
    public Deadlines(String taskName, LocalDateTime deadline) {
        super(taskName);
        this.deadline = deadline;
    }

    @Override
    public String storeTask() {
        return String.format("DEADLINE | %s | %s | %s", status.toString(), super.taskName, deadline.format(super.saveFormat));
    }

    @Override
    public String toString() {
        String statusIcon = status ? "X" : " "; // X if true, space if false
        return String.format("[D] [%s] %s Datetime: %s",
                statusIcon, taskName, deadline.format(super.saveFormat));
    }
}
