/**
 * Coordinates Duke's command processing and task list.
 *
 * <p>Tasks live in memory only: this version of the chatbot has no storage, so the
 * list starts empty on every launch and is not written back to disk.
 */
public class Duke {
    /** Style hint for commands that add a task. */
    private static final String ADD_COMMAND = "AddCommand";

    /** Style hint for commands that change whether a task is done. */
    private static final String CHANGE_MARK_COMMAND = "ChangeMarkCommand";

    /** Style hint for commands that delete a task. */
    private static final String DELETE_COMMAND = "DeleteCommand";

    private final TaskList tasks = new TaskList();
    private final Ui ui = new Ui();

    private String commandType;

    public static void main(String[] args) {
        System.out.println(new Duke().getWelcomeMessage());
    }

    /**
     * Returns Duke's greeting, shown when the GUI opens.
     */
    public String getWelcomeMessage() {
        commandType = null;
        return ui.getWelcome();
    }

    /**
     * Generates a response for the user's chat message and records the type of
     * command that was recognised.
     */
    public String getResponse(String input) {
        try {
            return processInput(input.trim());
        } catch (DukeException e) {
            commandType = null;
            return ui.getError(e.getMessage());
        }
    }

    /**
     * Returns a hint describing the command handled by the last call to
     * {@link #getResponse(String)}, or {@code null} when the reply needs no
     * special styling. The GUI uses it to tint Duke's dialog box.
     */
    public String getCommandType() {
        return commandType;
    }

    /**
     * Executes one command and returns the text Duke should reply with.
     */
    private String processInput(String input) throws DukeException {
        if (input.isEmpty()) {
            throw new DukeException("OOPS!!! Please type a command.");
        }

        commandType = null;
        Command command = Parser.getCommand(input);
        switch (command) {
        case BYE:
            return ui.getGoodbye();
        case LIST:
            return ui.getTaskList(tasks);
        case MARK:
            return markTask(input);
        case UNMARK:
            return unmarkTask(input);
        case DELETE:
            return deleteTask(input);
        case FIND:
            return findTasks(input);
        case TODO:
            return addTask(Parser.parseTodo(input));
        case DEADLINE:
            return addTask(Parser.parseDeadline(input));
        case EVENT:
            return addTask(Parser.parseEvent(input));
        default:
            throw new DukeException("OOPS!!! I don't know what that means. "
                    + "Try todo, deadline, event, list, find, mark, unmark, or delete.");
        }
    }

    /**
     * Adds a task and tells the user about the updated list.
     */
    private String addTask(Task task) {
        tasks.add(task);
        commandType = ADD_COMMAND;
        return ui.getTaskAdded(task, tasks.size());
    }

    /**
     * Marks the requested task as done.
     */
    private String markTask(String input) throws DukeException {
        int index = Parser.parseTaskIndex(input, Command.MARK, tasks.size());
        Task task = tasks.get(index);
        task.markAsDone();
        commandType = CHANGE_MARK_COMMAND;
        return ui.getTaskMarked(task);
    }

    /**
     * Marks the requested task as not done yet.
     */
    private String unmarkTask(String input) throws DukeException {
        int index = Parser.parseTaskIndex(input, Command.UNMARK, tasks.size());
        Task task = tasks.get(index);
        task.markAsNotDone();
        commandType = CHANGE_MARK_COMMAND;
        return ui.getTaskUnmarked(task);
    }

    /**
     * Deletes the requested task.
     */
    private String deleteTask(String input) throws DukeException {
        int index = Parser.parseTaskIndex(input, Command.DELETE, tasks.size());
        Task removedTask = tasks.remove(index);
        commandType = DELETE_COMMAND;
        return ui.getTaskDeleted(removedTask, tasks.size());
    }

    /**
     * Shows tasks whose descriptions contain the requested keyword.
     */
    private String findTasks(String input) throws DukeException {
        String keyword = Parser.parseFindKeyword(input);
        return ui.getMatchingTasks(tasks.find(keyword));
    }
}
