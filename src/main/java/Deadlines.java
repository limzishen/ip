public class Deadlines extends Task{
    String deadline;

    public Deadlines (String taskName, Boolean status, String deadline) {
        super(taskName, status);
        this.deadline = deadline;
    }
    public Deadlines(String taskName, String deadline) {
        super(taskName);
        this.deadline = deadline;
    }

    @Override
    public String storeTask() {
        return String.format("DEADLINE | %s | %s | %s", status.toString(), super.taskName, deadline);
    }

    @Override
    public String toString() {
        String statusIcon = status ? "X" : " "; // X if true, space if false
        return String.format("[D] [%s] %s Datetime: %s",
                statusIcon, taskName, deadline);
    }
}
