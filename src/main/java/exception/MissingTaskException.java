package exception;

public class MissingTaskException extends Exception{
    public MissingTaskException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        // Adds a custom prefix to every error message automatically
        return " ☹ OOPS!!! " + super.getMessage();
    }
}
