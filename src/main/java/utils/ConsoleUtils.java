package utils;

import java.util.Scanner;

/**
 * Console helper to get user input
 */
public class ConsoleUtils {

    /**
     * Internal scanner linked to System.in
     */
    private static Scanner in;

    /**
     * Asks for confirmation for some action.
     *
     * @param prompt    Action prompt
     * @return          True if yes; false if no.
     */
    public static boolean getConfirmation(String prompt) {
        setupScanner();
        System.out.print(prompt + " (y/n): ");
        String confirmationInput = in.nextLine();
        return confirmationInput.equalsIgnoreCase("y") ||
                confirmationInput.equalsIgnoreCase("yes");
    }

    /**
     * Asks the user to select one of the available options.
     * Doesn't perform validation on the user's choice.
     * Should be done client-side.
     *
     * @param actions   Actions varargs
     * @return          An action selected by user
     */
    public static String promptAction(String... actions) {
        if(actions.length <= 0) {
            throw new IllegalArgumentException("No available actions provided.");
        }
        setupScanner();
        StringBuilder promptString = new StringBuilder("Please enter an action. Available choices [");
        for(String action: actions) {
            promptString.append(action).append(", ");
        }
        promptString.setLength(promptString.length() - 2);
        promptString.append("]: ");
        System.out.print(promptString.toString());
        return in.nextLine();
    }

    /**
     * Initializes the internal scanner, if needed.
     */
    private static void setupScanner() {
        if(in == null) {
            in = new Scanner(System.in);
        }
    }
}
