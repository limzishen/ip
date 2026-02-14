package engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import exception.InvalidActionException;
import task.Deadlines;
import task.Event;
import task.Task;

/**
 * Handles loading and saving tasks to a file on the hard disk.
 */
public class Storage {
    private final File file;

    /**
     * Constructs a Storage instance. Creates the file and parent directories if they do not exist.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.file = new File(filePath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            createFile();
        }
    }

    /**
     * Reads tasks from the storage file and returns them as a TaskList.
     *
     * @return A TaskList containing the tasks loaded from the file.
     */
    public TaskList readFromFile() {
        TaskList tasks = new TaskList();
        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(" \\| ");
                Task task;
                switch (details[0]) {
                case "TODO":
                    task = new Task(details[2], Boolean.parseBoolean(details[1]));
                    break;
                case "EVENT":
                    task = new Event(details[2], Boolean.parseBoolean(details[1]), Parser.convertDateTimeString(details[3]), Parser.convertDateTimeString(details[4]));
                    break;
                case "DEADLINE":
                    task = new Deadlines(details[2], Boolean.parseBoolean(details[1]), Parser.convertDateTimeString(details[3]));
                    break;
                default:
                    continue;
                }
                tasks.addTask(task);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found! Check the path.");
        } catch (InvalidActionException e) {
            System.out.printf("Error: %s%n\n", e.getMessage());
            System.out.println();
        }
        return tasks;
    }


    /**
     * Creates a new, empty file.
     */
    public void createFile() {
        try {
            if (file.createNewFile()) {
                System.out.println("File created successfully: " + file.getAbsolutePath());
            } else {
                System.out.println("File already existed (unexpected in this logic path).");
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    /**
     * Writes the current list of tasks to the specified file.
     * Each task is converted to a string using its {@code storeTask()} method.
     *
     * @param taskList The list of tasks to be saved.
     */
    public void writeFile(TaskList taskList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : taskList.getList()) {
                writer.write(task.storeTask());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }


}
