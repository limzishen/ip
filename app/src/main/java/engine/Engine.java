package engine;

import task.Task;
import exception.InvalidActionException;
import exception.MissingTaskException;

public class Engine {
    private final Storage storage;
    private final TaskList tasks;
    String filePath = "src/data/data.txt";

    public Engine(String filePath) {
        storage = new Storage(filePath);
        tasks = storage.readFromFile();
    }

    /**
     * Executes the command based on the parsed command type and details.
     *
     * @param cmd     The parsed Command enum.
     * @param details The details string associated with the command.
     * @return The response string to be displayed to the user.
     * @throws InvalidActionException If the command is invalid or arguments are incorrect.
     * @throws MissingTaskException   If a requested task does not exist.
     */
    public String executeCommandAndGetResponse(Command cmd, String details) throws InvalidActionException, MissingTaskException {
        try {
            switch (cmd) {
            case LIST:
                return getListString();
            case MARK:
                int markIndex = Integer.parseInt(details) - 1;
                return markTask(markIndex, true);
            case UNMARK:
                int unmarkIndex = Integer.parseInt(details) - 1;
                return markTask(unmarkIndex, false);
            case DELETE:
                int deleteIndex = Integer.parseInt(details) - 1;
                return deleteTask(deleteIndex);
            case CLEAR:
             clearTasks();
                return "All tasks cleared!";
            case TODO:
            case DEADLINE:
            case EVENT:
                return addTask(cmd, details);
            case FIND:
                return findTasks(details);
            case UNKNOWN:
            default:
                throw new InvalidActionException("I don't understand that command. Try 'list', 'todo', 'deadline', 'event', 'mark', 'unmark', 'delete', 'find', 'clear', or 'exit'.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidActionException("Invalid task number format.");
        }
    }

    /**
     * Gets the list of all tasks as a formatted string.
     *
     * @return A string representation of all tasks.
     */
    public String getListString() {
        return tasks.printList();
    }

    /**
     * Marks or unmarks a task as done/not done.
     *
     * @param index The 0-based index of the task.
     * @param isDone True to mark as done, false to mark as not done.
     * @return A message confirming the action.
     * @throws IndexOutOfBoundsException If the index is invalid.
     */
    public String markTask(int index, boolean isDone) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds";
        Task task = tasks.get(index);
        task.status = isDone;
        String status = isDone ? "marked as done" : "marked as not done";
        return "Got it. I've " + status + ":\n  " + task;
    }

    /**
     * Deletes a task from the list.
     *
     * @param index The 0-based index of the task to delete.
     * @return A message confirming the deletion.
     * @throws IndexOutOfBoundsException If the index is invalid.
     */
    public String deleteTask(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds";
        Task task = tasks.get(index);
        tasks.remove(index);
        return "Noted. I've removed this task:\n  " + task + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Adds a new task to the list.
     *
     * @param cmd The command type (TODO, DEADLINE, or EVENT).
     * @param details The task details/description.
     * @return A message confirming the task was added.
     * @throws InvalidActionException If task creation fails.
     * @throws MissingTaskException If required task details are missing.
     */
    public String addTask(Command cmd, String details) throws InvalidActionException, MissingTaskException {
        Task newTask = Parser.parseTask(cmd, details);
        tasks.addTask(newTask);
        return "Got it. I've added this task:\n  " + newTask + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Finds tasks matching a keyword.
     *
     * @param keyword The search keyword.
     * @return A formatted string of matching tasks.
     */
    public String findTasks(String keyword) {
        TaskList temp = tasks.find(keyword);
        if (temp.isEmpty()) {
            return "No matching tasks found.";
        }
        StringBuilder result = new StringBuilder("Here are the matching tasks:\n");
        for (int i = 0; i < temp.size(); i++) {
            result.append((i + 1)).append(". ").append(temp.get(i)).append("\n");
        }
        return result.toString();
    }

    /**
     * Clears all tasks from the list.
     */
    public void clearTasks() {
        tasks.clearList();
    }

    /**
     * Saves all tasks to the storage file.
     */
    public void write() {
        assert filePath != null : "File path should not be null";
        storage.writeFile(tasks);
    }

}
