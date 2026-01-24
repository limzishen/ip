package task;

import java.time.format.DateTimeFormatter;

public class Task {
    public Boolean status;
    public String taskName;
    public static final DateTimeFormatter saveFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Constructs a new Task with a specified name and completion status.
     *
     * @param taskName The name or description of the task.
     * @param status The initial completion status.
     */
    public Task(String taskName, boolean status) {
        this.taskName = taskName;
        this.status = status;
    }

    /**
     * Constructs a new Task with a specified name.
     * The status is initialized to false (not done) by default.
     *
     * @param taskName The name or description of the task.
     */
    public Task(String taskName) {
        this(taskName, false);
    }

    /**
     * Generates a confirmation message to be displayed when a task is successfully added.
     *
     * @param taskCount The current total number of tasks in the list.
     * @return A formatted string confirming the task addition and the new list size.
     */
    public String taskAddedMessage(int taskCount) {
        return String.format(
                "Got it. I've added this task: \n " +
                        "%s" +
                        "\n Now you have %d tasks in the list.",
                this.toString(), taskCount);
    }

    /**
     * Formats the task into a specific string format suitable for file storage.
     * The standard format is "TYPE | status | taskName".
     *
     * @return A string representing the task in a storable format.
     */
    public String storeTask() {
        return String.format("TODO | %s | %s", status.toString(), taskName);
    }


    /**
     * Returns a string representation of the task for console display.
     * Includes a type icon [T] and a status icon [X] for done or [ ] for not done.
     *
     * @return A user-friendly string representation of the task.
     */
    @Override
    public String toString() {
        String statusIcon = status ? "X" : " "; // X if true, space if false
        return String.format("[T] [%s] %s",
                statusIcon, taskName);
    }

}