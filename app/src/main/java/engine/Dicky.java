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