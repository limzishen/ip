import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Dicky {

    public static void main(String[] args) {
        String line = "------------------------------";
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>(); // [task, status]

        //greet
        String welcomeMessage = "Hello I'm Dicky.";
        System.out.println(line);
        System.out.println(welcomeMessage);
        System.out.println(line);

        // Scan for input
        while (scanner.hasNextLine()) {
            String action = scanner.nextLine().strip();
            String[] input = action.split("\\s+");

            if (action.equals("list")) {
                if (tasks.isEmpty()) {
                    System.out.println("No items in list");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.printf("%d: %s%n", i + 1, tasks.get(i));
                    }
                    System.out.println(line);
                }
            } else if (input[0].equals("mark")){
                int index = Integer.parseInt(input[1]) - 1;
                tasks.get(index).status = true;

                System.out.println("Nice! I've marked this task as done:");
                System.out.println(tasks.get(index));

            } else if (input[0].equals("unmark")) {
                int index = Integer.parseInt(input[1]) - 1;
                tasks.get(index).status = false;

                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(tasks.get(index));
            } else if (action.equalsIgnoreCase("exit")) {
                break; // Exit the loop if the user types 'exit'
            } else{
                Task newTask = null;
                switch (input[0].toLowerCase()) {
                    case "todo":
                        String[] slice = Arrays.copyOfRange(input, 1, input.length);
                        newTask = new Task(String.join(" ", slice));
                        break;
                    case "deadline":
                        newTask = new Deadlines(input[1], input[2]);
                        break;
                    case "event":
                        newTask = new Event(input[1], input[2], input[3]);
                    default:
                        newTask = new Task("invalid task");
                }
                tasks.add(newTask);
                System.out.println(line);
                System.out.println(newTask.taskAddedMessage(tasks.size()));
                System.out.println(line);

            }
        }

        // Exit
        System.out.println(line);
        String exitMessage = "Bye. Hope to see you again soon!";
        System.out.println(exitMessage);
        System.out.println(line);
    }
}
