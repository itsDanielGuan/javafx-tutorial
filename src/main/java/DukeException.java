/**
 * Represents an error caused by invalid user input in the Duke chatbot.
 */
public class DukeException extends Exception {
    /** Version identifier used when an exception is serialized. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a Duke-specific exception with a user-friendly message.
     */
    public DukeException(String message) {
        super(message);
    }
}
