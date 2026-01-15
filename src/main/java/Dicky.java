import java.util.Scanner;

public class Dicky {
    public static void main(String[] args) {
        String line = "------------------------------";
        Scanner scanner = new Scanner(System.in);

        //greet
        String welcomeMessage = "Hello I'm Dicky.";
        System.out.println(line);
        System.out.println(welcomeMessage);
        System.out.println(line);

        // Scan for input
        while (scanner.hasNextLine()) {
            String action = scanner.nextLine();

            if (action.equalsIgnoreCase("pineapple")) {
                break; // Exit the loop if the user types 'exit'
            }
            System.out.println(line + "\n" + action + "\n" + line);
        }


        // Exit
        System.out.println(line);
        String exitMessage = "Bye. Hope to see you again soon!";
        System.out.println(exitMessage);
        System.out.println(line);
    }
}
