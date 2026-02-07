package engine;

import task.Task;
import ui.Ui;

import exception.InvalidActionException;
import exception.MissingTaskException;

/**
 * The main class for the Dicky chatbot application.
 * It coordinates the UI, storage, and task management logic.
 */
public class Dicky {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    String filePath = "src/data/data.txt";

    /**
     * Constructs a Dicky instance with a specified file path for data storage.
     *
     * @param filePath The path to the file where tasks are saved and loaded.
     */
    public Dicky(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = storage.readFromFile();
    }

    /**
     * Starts the main application loop.
     * Processes user input, executes commands, and handles exceptions until the exit command is given.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command cmd = Parser.parseCommand(fullCommand);
                String details = Parser.parseDetails(fullCommand);

                if (cmd == Command.EXIT) {
                    isExit = true;
                    ui.showExit();
                    storage.writeFile(tasks);
                } else {
                    executeCommand(cmd, details);
                }
            } catch (Exception e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Executes the logic for a specific command.
     *
     * @param cmd The command type to execute.
     * @param details The arguments or description associated with the command.
     * @throws InvalidActionException If the command is unrecognized or parameters are invalid.
     * @throws MissingTaskException If required task details are missing.
     */
    private void executeCommand(Command cmd, String details) throws InvalidActionException, MissingTaskException {
        switch (cmd) {
        case LIST:
            ui.printList(tasks);
            break;
        case MARK:
        case UNMARK:
            int index = Integer.parseInt(details) - 1;
            boolean isDone = (cmd == Command.MARK);
            tasks.get(index).status = isDone;
            ui.markMessage(isDone, tasks.get(index));
            break;
        case DELETE:
            int deleteIndex = Integer.parseInt(details) - 1;
            Task task = tasks.get(deleteIndex);
            tasks.remove(deleteIndex);
            ui.showDeletedMessage(task);
            break;
        case CLEAR:
            tasks.clearList();
            ui.clearList();
            break;
        case TODO:
        case DEADLINE:
        case EVENT:
            Task newTask = Parser.parseTask(cmd, details);
            tasks.addTask(newTask);
            ui.showMessage("Got it. I've added this task:\n  " + newTask + "\nNow you have " + tasks.size() + " tasks in the list.\n");
            break;
        case FIND:
            TaskList temp = tasks.find(details);
            ui.printList(temp);
            break;
        case UNKNOWN:
        default:
            throw new InvalidActionException("Invalid Action: " + cmd);
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
        if (temp.size() == 0) {
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
     *
     * @return A confirmation message.
     */
    public void clearTasks() {
        tasks.clearList();
    }

    /**
     * Saves all tasks to the storage file.
     */
    public void write() {
        storage.writeFile(tasks);
    }

    /**
     * The entry point of the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        String filePath = "src/data/data.txt";
        if (args.length > 0) {
            filePath = args[0];
        }
        new Dicky(filePath).run();
    }
}