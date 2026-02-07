package engine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import task.Deadlines;
import task.Event;
import task.Task;
import exception.InvalidActionException;
import exception.MissingTaskException;

/**
 * Deals with making sense of the user command.
 */
public class Parser {

    /**
     * Parses the command type from the first word of input.
     *
     * @param input The raw user input string.
     * @return The corresponding Command enum, or UNKNOWN if not recognized.
     */
    public static Command parseCommand(String input) {
        String commandWord = input.split("\\s+")[0].toUpperCase();
        try {
            return Command.valueOf(commandWord);
        } catch (IllegalArgumentException e) {
            return Command.UNKNOWN;
        }
    }

    /**
     * Extracts the details or arguments following the command word.
     *
     * @param input The raw user input string.
     * @return The string containing the command arguments.
     */
    public static String parseDetails(String input) {
        String[] details = input.split("\\s+");
        Command cmd = parseCommand(input);
        String result ="";

        switch (cmd) {
        case MARK:
        case UNMARK:
        case DELETE:
            result = details[1];
            break;
        case FIND:
        case TODO:
        case DEADLINE:
        case EVENT:
            result = String.join(" ", Arrays.copyOfRange(details, 1, details.length));
            break;
        case LIST:
        case CLEAR:
        case UNKNOWN:
        case EXIT:
            break;
        }

        return result;
    }


    /**
     * Parses raw input into a Task object based on the command type.
     *
     * @param cmd The command type (TODO, DEADLINE, or EVENT).
     * @param details The description and timing details of the task.
     * @return A Task object of the appropriate subtype.
     * @throws InvalidActionException If the format is incorrect.
     * @throws MissingTaskException If the details are empty.
     */
    public static Task parseTask(Command cmd, String details)
            throws InvalidActionException, MissingTaskException {

        if (details == "") {
            throw new MissingTaskException("The description of a " + cmd + " cannot be empty.");
        }

        return switch (cmd) {
            case TODO -> new Task(details);
            case DEADLINE -> parseDeadline(details);
            case EVENT -> parseEvent(details);
            default -> throw new InvalidActionException("I don't know how to create a task for " + cmd);
        };
    }

    private static Deadlines parseDeadline(String content) throws InvalidActionException {
        String[] parts = content.split(" /by ");
        if (parts.length < 2) {
            throw new InvalidActionException("Missing deadline! Use: /by yyyy-MM-dd HHmm");
        }
        LocalDateTime due = convertDateTimeString(parts[1]);
        return new Deadlines(parts[0], due);
    }

    private static Event parseEvent(String content) throws InvalidActionException {
        String[] parts = content.split(" /from | /to ");
        if (parts.length < 3) {
            throw new InvalidActionException("Missing event timing! Use: /from [start] /to [end]");
        }
        LocalDateTime start = convertDateTimeString(parts[1]);
        LocalDateTime end = convertDateTimeString(parts[2]);
        return new Event(parts[0], start, end);
    }

    /**
     * Converts a date-time string into a LocalDateTime object.
     *
     * @param dateTimeString The string to parse, expected in "yyyy-MM-dd HHmm" format.
     * @return The parsed LocalDateTime object.
     * @throws InvalidActionException If the string does not match the expected format.
     */
    public static LocalDateTime convertDateTimeString(String dateTimeString) throws InvalidActionException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime localDateTime = null;
        try {
            localDateTime = LocalDateTime.parse(dateTimeString.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidActionException("Invalid date format! Use: yyyy-MM-dd HHmm");
        }
        return localDateTime;
    }

}