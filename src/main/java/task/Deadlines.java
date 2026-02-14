package task;

import java.time.LocalDateTime;

public class Deadlines extends Task {
    LocalDateTime deadline;

    /**
     * Constructs a Deadlines task with a specified name, status, and due date.
     *
     * @param taskName The name or description of the task.
     * @param status   The completion status of the task.
     * @param deadline The {@link LocalDateTime} representing the due date.
     */
    public Deadlines (String taskName, Boolean status, LocalDateTime deadline) {
        super(taskName, status);
        this.deadline = deadline;
    }

    /**
     * Constructs a Deadlines task with a specified name and due date.
     * The status is initialized to false (not done) by default.
     *
     * @param taskName The name or description of the task.
     * @param deadline The {@link LocalDateTime} representing the due date.
     */
    public Deadlines(String taskName, LocalDateTime deadline) {
        super(taskName);
        this.deadline = deadline;
    }

    /**
     * Formats the deadline task into a string suitable for file storage.
     * Uses the "DEADLINE | status | taskName | deadline" format.
     * The date is formatted using the {@code saveFormat} inherited from the {@link Task} class.
     *
     * @return A pipe-delimited string representing the deadline task for storage.
     */
    @Override
    public String storeTask() {
        return String.format("DEADLINE | %s | %s | %s", status.toString(), super.taskName, deadline.format(Task.SAVE_FORMAT));
    }

    /**
     * Returns a string representation of the deadline task for console display.
     * Includes the [D] tag, status icon, and the formatted deadline date.
     *
     * @return A user-friendly string representation of the deadline.
     */
    @Override
    public String toString() {
        String statusIcon = status ? "X" : " "; // X if true, space if false
        return String.format("[D] [%s] %s Datetime: %s",
                statusIcon, taskName, deadline.format(Task.SAVE_FORMAT));
    }
}
