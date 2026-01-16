public class Task {
    public Boolean status;
    public String taskName;

    public Task(String taskName, boolean status) {
        this.taskName = taskName;
        this.status = status;
    }

    public Task(String taskName) {
        this(taskName, false);
    }

    @Override
    public String toString() {
        if (status) {
            return "[X] " + taskName;
        } else {
            return "[] " + taskName;
        }
    }
}
