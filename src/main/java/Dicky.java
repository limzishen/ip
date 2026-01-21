import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import exception.InvalidActionException;
import exception.MissingTaskException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Dicky {
    /**
     * Reads and prints the content of the file.
     * @param file The file to read.
     */
    private static ArrayList<Task> readFromFile(File file) {
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
                        task = new Event(details[2], Boolean.parseBoolean(details[1]), details[3], details[4]);
                        break;
                    case "DEADLINE":
                        task = new Deadlines(details[2], Boolean.parseBoolean(details[1]), details[3]);
                        break;
                    default:
                        continue;
                }
                tasks.add(task);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found! Check the path.");
        }
        return tasks;
    }


    /**
     * Creates a new, empty file.
     * @param file The file to create.
     */
    private static void createFile(File file) {
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

    private static void writeFile(ArrayList<Task> TaskList, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : TaskList) {
                writer.write(task.storeTask());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        String filePath = "src/data/data.txt";
        File file = new File(filePath);
        ArrayList<Task> tasks;

        if (file.exists()) {
            System.out.println("File exists. Reading from file...");
            tasks = readFromFile(file);
        } else {
            System.out.println("File does not exist. Creating file...");
            createFile(file);
            tasks = new ArrayList<>();
        }

        String line = "------------------------------";
        Scanner scanner = new Scanner(System.in);


        //greet
        String welcomeMessage = "Hello I'm Dicky.";
        System.out.println(line);
        System.out.println(welcomeMessage);
        System.out.println(line);

        // Scan for input
        while (scanner.hasNextLine()) {
            System.out.println(line);
            try {
                String action = scanner.nextLine().strip();
                if (action.isEmpty()) continue;

                String[] input = action.split("\\s+");
                Command cmd = Command.fromString(input[0]);
                int index;

                switch (cmd) {
                    case LIST:
                        if (tasks.isEmpty()) {
                            System.out.println("No items in list");
                        } else {
                            for (int i = 0; i < tasks.size(); i++) {
                                System.out.printf("%d: %s%n", i + 1, tasks.get(i));
                            }
                        }
                        break;

                    case MARK:
                    case UNMARK:
                        index = Integer.parseInt(input[1]) - 1;
                        boolean isDone = (cmd == Command.MARK);
                        tasks.get(index).status = isDone;

                        System.out.println(isDone ? "Nice! I've marked this task as done:"
                                : "OK, I've marked this task as not done yet:");
                        System.out.println(tasks.get(index));
                        break;
                    case DELETE:
                        Task task = tasks.get(Integer.parseInt(input[1]) - 1);
                        index = Integer.parseInt(input[1]) - 1;
                        tasks.remove(index);
                        System.out.printf("Task removed: %s \n", task.toString());
                        break;
                    case EXIT:
                        writeFile(tasks, file);
                        return; // or break the loop

                    case TODO:
                    case DEADLINE:
                    case EVENT:
                        if (input.length < 2) throw new MissingTaskException("No task");
                        String splice = String.join(" ", Arrays.copyOfRange(input, 1, input.length));
                        Task newTask = switch (cmd) {
                            case TODO -> new Task(splice);
                            case DEADLINE -> {
                                String[] deadlinesPart = splice.split(" /by ");
                                if (deadlinesPart.length < 2) throw new InvalidActionException("missing deadline");
                                yield new Deadlines(deadlinesPart[0], deadlinesPart[1]);
                            }
                            case EVENT -> {
                                String[] eventParts = splice.split(" /from | /to ");
                                if (eventParts.length < 3) throw new InvalidActionException("missing start/end time");
                                yield new Event(eventParts[0], eventParts[1], eventParts[0]);
                            }
                            default -> throw new InvalidActionException("Unexpected error");
                        };
                        tasks.add(newTask);
                        System.out.println(newTask.taskAddedMessage(tasks.size()));
                        break;

                    case UNKNOWN:
                    default:
                        throw new InvalidActionException("Invalid Action: " + input[0]);
                }
            } catch (InvalidActionException | MissingTaskException e) {
                System.out.printf("Error: %s%n\n", e.getMessage());
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println("Error: Please provide a valid task number.");
            }
            System.out.println(line);
        }

        // Exit
        System.out.println(line);
        String exitMessage = "Bye. Hope to see you again soon!";
        System.out.println(exitMessage);
        System.out.println(line);
    }
}
