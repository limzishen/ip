class Event extends Task{
    public String startTime;
    public String endTime;
    public Event(String taskName, String startTime, String endTime) {
        super(taskName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        String statusIcon = status ? "X" : " "; // X if true, space if false
        return String.format("[E] [%s] %s Start Time: %s End Time: %s",
                statusIcon, taskName, startTime, endTime);
    }

}
