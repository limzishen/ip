import java.util.ArrayList;
import java.util.Scanner;

public class Dicky {

    public static void main(String[] args) {
        String line = "------------------------------";
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>();

        //greet
        String welcomeMessage = "Hello I'm Dicky.";
        System.out.println(line);
        System.out.println(welcomeMessage);
        System.out.println(line);

        // Scan for input
        while (scanner.hasNextLine()) {
            String action = scanner.nextLine();

            if (action.equals("list")) {
                if (tasks.isEmpty()) {
                    System.out.println("No items in list");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.printf("%d: %s%n", i + 1, tasks.get(i));
                    }
                }
            }

            if (action.equalsIgnoreCase("exit")) {
                break; // Exit the loop if the user types 'exit'
            }

            tasks.add(action);
            System.out.println(line + "\n" + action + "\n" + line);
        }

        // Exit
        System.out.println(line);
        String exitMessage = "Bye. Hope to see you again soon!";
        System.out.println(exitMessage);
        System.out.println(line);
    }
}
