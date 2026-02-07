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
     * Displays the welcome message to the user.
     */
    public void showWelcome() {
        System.out.println(LINE + "\nHello I'm Dicky.\n" + LINE);
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
     * Prints a horizontal line separator to the console.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Displays a general message to the user.
     *
     * @param message The message to be displayed.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays a message confirming that a task's status has been updated.
     *
     * @param isDone The new status of the task (true if done, false otherwise).
     * @param task   The task that was updated.
     */
    public void markMessage(Boolean isDone, Task task) {
        if (isDone) {
            System.out.println("Nice! I've marked this task as done:");
        } else {
            System.out.println("OK, I've marked this task as not done yet:"); 
        }
        System.out.println(task.toString() + "\n");
    }

    /**
     * Displays a message confirming that the task list has been cleared.
     */
    public void clearList() {
        System.out.println("List Cleared");
    }

    public void showDeletedMessage(Task task) {
        System.out.println("OK, I've deleted this task:");
        System.out.println(task.toString() + "\n");
    }

    /**
     * Prints the formatted list of tasks to the console.
     *
     * @param tasks The TaskList containing tasks to be printed.
     */
    public void printList(TaskList tasks) {
        System.out.print(tasks.printList());
    }

    public void showExit() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}