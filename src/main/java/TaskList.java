import Task.Task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return this.tasks.get(index);
    }

    public void remove(int index) throws IndexOutOfBoundsException {
        this.tasks.remove(index);
    }

    public ArrayList<Task> getList() {
        return this.tasks;
    }

    public String printList() {
        StringBuilder sb = new StringBuilder();
        if (tasks.isEmpty()) {
            sb.append("No items in list");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                sb.append(String.format("%d: %s \n", i + 1, tasks.get(i)));
            }
        }
        return sb.toString();
    }
}
