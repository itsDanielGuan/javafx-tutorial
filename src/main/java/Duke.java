/**
 * The chatbot's main logic.
 *
 * <p>This tutorial version has no {@code Parser}/{@code Command} classes yet, so it
 * classifies the input itself and reports the command type the real Duke would have
 * produced. The GUI uses that type to style Duke's reply.
 */
public class Duke {

    private String commandType;

    public static void main(String[] args) {
        System.out.println("Hello!");
    }

    /**
     * Generates a response for the user's chat message and records the type of
     * command that was recognised.
     */
    public String getResponse(String input) {
        commandType = parseCommandType(input);
        return "Duke heard: " + input;
    }

    /**
     * Returns the simple class name of the command handled by the last call to
     * {@link #getResponse(String)}, or {@code null} if the input was not a known command.
     */
    public String getCommandType() {
        return commandType;
    }

    private String parseCommandType(String input) {
        String keyword = input.trim().split("\s+", 2)[0].toLowerCase();

        return switch (keyword) {
        case "todo", "deadline", "event" -> "AddCommand";
        case "mark", "unmark" -> "ChangeMarkCommand";
        case "delete" -> "DeleteCommand";
        default -> null;
        };
    }
}
