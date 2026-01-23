package exception;

public class InvalidActionException extends Exception{
    public InvalidActionException (String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        // Adds a custom prefix to every error message automatically
        return " ☹ OOPS!!! " + super.getMessage();
    }

}
