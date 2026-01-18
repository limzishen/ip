public class Deadlines extends Task{
    String deadline;
    public Deadlines(String taskName, String deadline) {
        super(taskName);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        String statusIcon = status ? "X" : " "; // X if true, space if false
        return String.format("[D] [%s] %s Datetime: %s",
                statusIcon, taskName, deadline);
    }
}
