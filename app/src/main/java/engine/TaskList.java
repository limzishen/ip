package engine;

import java.util.ArrayList;

import task.Task;

public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList containing the elements of the specified collection.
     *
     * @param tasks The initial list of tasks to be managed.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The {@link Task} to be added.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Checks if the task list is empty.
     *
     * @return {@code true} if the list contains no tasks, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Retrieves the task at the specified position in the list.
     *
     * @param index The zero-based index of the task to return.
     * @return The {@link Task} at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public Task get(int index) {
        return this.tasks.get(index);
    }

    /**
     * Removes all tasks from the list, resulting in an empty list.
     */
    public void clearList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Removes the task at the specified position in this list.
     *
     * @param index The zero-based index of the task to be removed.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public void remove(int index) throws IndexOutOfBoundsException {
        this.tasks.remove(index);
    }

    /**
     * Returns the underlying list of tasks.
     * Used primarily for saving data to storage.
     *
     * @return An {@code ArrayList} containing all tasks.
     */
    public ArrayList<Task> getList() {
        return this.tasks;
    }

    public TaskList find(String keyword) {
        TaskList temp = new TaskList();

        for (Task task : tasks) {
            if (task.taskName.contains(keyword)) {
                temp.addTask(task);
            }
        }
        return temp;
    }

    /**
     * Formats the entire task list into a user-friendly string for display.
     * If the list is empty, returns a specific message indicating no items.
     *
     * @return A numbered string representation of all tasks in the list.
     */
    public String printList() {
        StringBuilder sb = new StringBuilder();
        if (tasks.isEmpty()) {
            sb.append("No items in list \n");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                sb.append(String.format("%d: %s \n", i + 1, tasks.get(i)));
            }
        }
        return sb.toString();
    }
}
