package ui;

import java.util.Scanner;
import engine.TaskList;
import task.Task;

/**
 * Handles the user interface of the application.
 * Responsible for reading user input and displaying messages to the console.
 */
public class Ui {
    private final Scanner scanner;
    private final String LINE = "------------------------------";

    /**
     * Constructs a Ui instance and initializes the scanner for user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns the welcome message to the user.
     *
     * @return The welcome message string.
     */
    public String getWelcome() {
        return LINE + "\nHello I'm Dicky.\n" + LINE;
    }

    /**
     * Reads a single line of command input from the user.
     *
     * @return The trimmed string input from the user, or an empty string if no input.
     */
    public String readCommand() {
        return scanner.hasNextLine() ? scanner.nextLine().strip() : "";
    }

    /**
     * Returns a horizontal line separator.
     *
     * @return A string representing a horizontal line.
     */
    public String getLine() {
        return LINE;
    }

    /**
     * Returns a formatted error message.
     *
     * @param message The error message to be displayed.
     * @return The formatted error string.
     */
    public String getError(String message) {
        return "Error: " + message;
    }

    /**
     * Returns a general message to the user.
     *
     * @param message The message to be displayed.
     * @return The message string.
     */
    public String getMessage(String message) {
        return message;
    }

    /**
     * Returns a message confirming that a task's status has been updated.
     *
     * @param isDone The new status of the task (true if done, false otherwise).
     * @param task   The task that was updated.
     * @return The formatted status update message.
     */
    public String getMarkMessage(Boolean isDone, Task task) {
        StringBuilder sb = new StringBuilder();
        if (isDone) {
            sb.append("Nice! I've marked this task as done:\n");
        } else {
            sb.append("OK, I've marked this task as not done yet:\n"); 
        }
        sb.append(task.toString()).append("\n");
        return sb.toString();
    }

    /**
     * Returns a message confirming that the task list has been cleared.
     *
     * @return The list cleared message.
     */
    public String getClearListMessage() {
        return "List Cleared";
    }

    /**
     * Returns a message confirming the deletion of a task.
     *
     * @param task The task that was deleted.
     * @return The formatted deletion message.
     */
    public String getDeletedMessage(Task task) {
        return "OK, I've deleted this task:\n" + task.toString() + "\n";
    }

    /**
     * Returns the formatted list of tasks.
     *
     * @param tasks The TaskList containing tasks to be printed.
     * @return A string representation of the task list.
     */
    public String getListString(TaskList tasks) {
        return tasks.printList();
    }

    /**
     * Returns the exit message.
     *
     * @return The exit message string.
     */
    public String getExitMessage() {
        return "Bye. Hope to see you again soon!";
    }
}