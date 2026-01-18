public class Task {
    public Boolean status;
    public String taskName;
    public Boolean deletedStatus;

    public Task(String taskName, boolean status) {
        this.taskName = taskName;
        this.status = status;
        this.deletedStatus = false;
    }

    public Task(String taskName) {
        this(taskName, false);
    }

    public String taskAddedMessage(int taskCount) {
        return String.format(
                "Got it. I've added this task: \n " +
                        "%s" +
                        "\n Now you have %d tasks in the list.",
                this.toString(), taskCount);
    }

    @Override
    public String toString() {
        String statusIcon = status ? "X" : " "; // X if true, space if false
        return String.format("[T] [%s] %s",
                statusIcon, taskName);
    }

}