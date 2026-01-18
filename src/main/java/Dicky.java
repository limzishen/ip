import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import exception.InvalidActionException;
import exception.MissingTaskException;

public class Dicky {

    public static void main(String[] args) {
        String line = "------------------------------";
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

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
