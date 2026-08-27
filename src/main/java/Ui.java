/**
 * Builds the text Duke shows to the user.
 *
 * <p>The console version of this chatbot printed to {@code System.out}. The GUI needs
 * the same wording as a value it can put inside a dialog box, so every method here
 * returns its message instead of printing it.
 */
public class Ui {
    private static final String NAME = "Duke";

    /**
     * Returns Duke's greeting.
     */
    public String getWelcome() {
        return "Hello! I'm " + NAME + ".\nWhat can I do for you?";
    }

    /**
     * Returns Duke's farewell message.
     */
    public String getGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns a user-friendly command error.
     */
    public String getError(String message) {
        return message;
    }

    /**
     * Returns every task in its current order using one-based numbering.
     */
    public String getTaskList(TaskList tasks) {
        if (tasks.size() == 0) {
            return "There are no tasks in your list yet.";
        }
        return "Here are the tasks in your list:\n" + getNumberedTasks(tasks);
    }

    /**
     * Returns the tasks whose descriptions matched a find keyword.
     */
    public String getMatchingTasks(TaskList matchingTasks) {
        if (matchingTasks.size() == 0) {
            return "There are no matching tasks in your list.";
        }
        return "Here are the matching tasks in your list:\n" + getNumberedTasks(matchingTasks);
    }

    /**
     * Returns the supplied tasks using one-based numbering.
     */
    private String getNumberedTasks(TaskList tasks) {
        StringBuilder numberedTasks = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            if (i > 0) {
                numberedTasks.append("\n");
            }
            numberedTasks.append(i + 1).append(".").append(tasks.get(i));
        }
        return numberedTasks.toString();
    }

    /**
     * Returns the confirmation that a task was added.
     */
    public String getTaskAdded(Task task, int taskCount) {
        return "Got it. I've added this task:\n  " + task
                + "\nNow you have " + taskCount + " tasks in the list.";
    }

    /**
     * Returns the confirmation that a task was marked as done.
     */
    public String getTaskMarked(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    /**
     * Returns the confirmation that a task was marked as not done.
     */
    public String getTaskUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    /**
     * Returns the confirmation that a task was deleted.
     */
    public String getTaskDeleted(Task task, int taskCount) {
        return "Noted. I've removed this task:\n  " + task
                + "\nNow you have " + taskCount + " tasks in the list.";
    }
}
