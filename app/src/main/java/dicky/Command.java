package dicky;

public enum Command {
    TODO, DEADLINE, EVENT, LIST, MARK, UNMARK, EXIT, DELETE, UNKNOWN;

    /**
     * Converts a string to a Command enum.
     * @param str The user input command word
     * @return The matching Command or UNKNOWN
     */
    public static Command fromString(String str) {
        try {
            return Command.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}