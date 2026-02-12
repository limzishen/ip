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
        System.out.println(ui.getWelcome());
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                System.out.println(ui.getLine());
                Command cmd = Parser.parseCommand(fullCommand);
                String details = Parser.parseDetails(fullCommand);

                if (cmd == Command.EXIT) {
                    isExit = true;
                    System.out.println(ui.getExitMessage());
                    storage.writeFile(tasks);
                } else {
                    executeCommand(cmd, details);
                }
            } catch (Exception e) {
                System.out.println(ui.getError(e.getMessage()));
            } finally {
                System.out.println(ui.getLine());
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
            System.out.print(ui.getListString(tasks));
            break;
        case MARK:
        case UNMARK:
            int index = Integer.parseInt(details) - 1;
            boolean isDone = (cmd == Command.MARK);
            tasks.get(index).status = isDone;
            System.out.println(ui.getMarkMessage(isDone, tasks.get(index)));
            break;
        case DELETE:
            int deleteIndex = Integer.parseInt(details) - 1;
            Task task = tasks.get(deleteIndex);
            tasks.remove(deleteIndex);
            System.out.println(ui.getDeletedMessage(task));
            break;
        case CLEAR:
            tasks.clearList();
            System.out.println(ui.getClearListMessage());
            break;
        case TODO:
        case DEADLINE:
        case EVENT:
            Task newTask = Parser.parseTask(cmd, details);
            tasks.addTask(newTask);
            System.out.println(ui.getMessage("Got it. I've added this task:\n  " + newTask + "\nNow you have " + tasks.size() + " tasks in the list.\n"));
            break;
        case FIND:
            TaskList temp = tasks.find(details);
            System.out.print(ui.getListString(temp));
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