package dicky;

import task.*;
import exception.InvalidActionException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    /**
     * Reads and prints the content of the file.
     * @param file The file to read.
     */
    public static ArrayList<Task> readFromFile(File file) {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!file.exists()) return tasks;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
                String[] details = line.split(" \\| ");
                Task task;
                switch (details[0]) {
                    case "TODO":
                        task = new Task(details[2], Boolean.parseBoolean(details[1]));
                        break;
                    case "EVENT":
                        task = new Event(details[2], Boolean.parseBoolean(details[1]), Storage.convertDateTimeString(details[3]), Storage.convertDateTimeString(details[4]));
                        break;
                    case "DEADLINE":
                        task = new Deadlines(details[2], Boolean.parseBoolean(details[1]), Storage.convertDateTimeString(details[3]));
                        break;
                    default:
                        continue;
                }
                tasks.add(task);
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
     * Convert String to LocalDateTime object
     * @param dateTimeString the string to convert to datetime object
     */
    public static LocalDateTime convertDateTimeString(String dateTimeString) throws InvalidActionException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime localDateTime = null;
        try {
            localDateTime = LocalDateTime.parse(dateTimeString.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidActionException("Invalid date format! Use: yyyy-MM-dd HHmm");
        }
        return localDateTime;
    }

    /**
     * Creates a new, empty file.
     * @param file The file to create.
     */
    public static void createFile(File file) {
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
     * @param TaskList The list of tasks to be saved.
     * @param file The file object where the data will be written.
     */
    public static void writeFile(ArrayList<Task> TaskList, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : TaskList) {
                writer.write(task.storeTask());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }




}
