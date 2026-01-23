import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import exception.InvalidActionException;
import exception.MissingTaskException;

import java.io.File;
import java.time.LocalDateTime;

public class Dicky {
    public static void main(String[] args) {
        String filePath = "src/data/data.txt";
        File file = new File(filePath);
        TaskList tasks;

        if (file.exists()) {
            System.out.println("File exists. Reading from file...");
            tasks = new TaskList(Storage.readFromFile(file));
        } else {
            System.out.println("File does not exist. Creating file...");
            Storage.createFile(file);
            tasks = new TaskList();
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
                        System.out.print(tasks.printList());
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
                        index = Integer.parseInt(input[1]) - 1;
                        Task task = tasks.get(index);
                        tasks.remove(index);
                        System.out.printf("Task removed: %s \n", task.toString());
                        break;

                    case EXIT:
                        Storage.writeFile(tasks.getList(), file);
                        String exitMessage = "Bye. Hope to see you again soon!";
                        System.out.println(exitMessage);
                        System.out.println(line);
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
                                LocalDateTime dtObject = Storage.convertDateTimeString(deadlinesPart[1]);
                                yield new Deadlines(deadlinesPart[0], dtObject);
                            }
                            case EVENT -> {
                                String[] eventParts = splice.split(" /from | /to ");
                                if (eventParts.length < 3) throw new InvalidActionException("missing start/end time");
                                LocalDateTime startTime = Storage.convertDateTimeString(eventParts[1]);
                                LocalDateTime endTime = Storage.convertDateTimeString(eventParts[2]);
                                yield new Event(eventParts[0], startTime, endTime);
                            }
                            default -> throw new InvalidActionException("Unexpected error");
                        };
                        tasks.addTask(newTask);
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
    }
}
