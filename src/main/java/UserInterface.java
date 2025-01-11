import java.util.Scanner;

/**
 * Handles user interaction, including displaying messages and capturing input.
 */
public class UserInterface {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the provided message to the user.
     *
     * @param message The message to display.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prompts the user for an input and returns it.
     *
     * @return The user input.
     */
    public String promptInput() {
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    /**
     * Displays a welcome message.
     */
    public void displayWelcomeMessage() {
        displayMessage("""
                ************************************************************************
                *                                                                      *
                *                   Welcome to Number Guessing Game!                   *
                *                                                                      *
                ************************************************************************
                """);
    }

    /**
     * Displays the rules of the game.
     */
    public void displayRules() {
        displayMessage("""
                - I will think of a number between 1 and 100, and you will have to guess
                  it within a limited number of attempts.
                - With each failed guess, I will give you feedback on whether the number
                  you said is higher or lower than the number I have thought of.
                """);
    }

    /**
     * Prompts the user to select a difficulty level and returns it.
     *
     * @return The selected difficulty level.
     */
    public Difficulty promptDifficultySelection() {
        displayMessage("""
                Select difficulty (easy/medium/hard):""");

        while (true) {
            String selection = promptInput().toLowerCase();

            switch (selection) {
                case "easy":
                    return Difficulty.EASY;
                case "medium":
                    return Difficulty.MEDIUM;
                case "hard":
                    return Difficulty.HARD;
                default:
                    displayMessage("""
                            Invalid difficulty. Please type 'easy', 'medium', or 'hard':""");
            }
        }
    }

    /**
     * Displays a message introducing the game round and informing on the
     * number of attempts allowed for the selected difficulty level.
     *
     * @param difficulty The selected difficulty level.
     */
    public void displayRoundStartMessage(Difficulty difficulty) {
        String message = String.format("""
                        Ok, I have already thought of a number between 1 and 100.
                        Since you chose the %s difficulty, you have %s to guess it.
                        Enter your guess:""",
                difficulty.toString().toLowerCase(),
                getFormattedAttempts(difficulty.getAttempts()));
        displayMessage(message);
    }

    /**
     * Returns a formatted string for the number of attempts.
     *
     * @param attempts The number of attempts.
     * @return A formatted string for the number of attempts.
     */
    private String getFormattedAttempts(int attempts) {
        if (attempts == 1) {
            return attempts + " attempt";
        }
        return attempts + " attempts";
    }

    /**
     * Prompts the user for a guess, validates it, and returns it.
     *
     * @return The validated guess entered by the user.
     */
    public int promptPlayerGuess() {
        while (true) {
            String input = promptInput();
            try {
                int guess = Integer.parseInt(input);
                if (guess < 1 || guess > 100) {
                    displayMessage("""
                            Invalid guess. Please enter a number between 1 and 100:""");
                } else {
                    return guess;
                }
            } catch (NumberFormatException e) {
                displayMessage("""
                        Invalid guess. Please enter a valid integer:""");
            }
        }
    }

    /**
     * Displays a winning message when the player guesses the number correctly.
     *
     * @param winningNumber The number that was guessed correctly.
     * @param attemptsUsed  The number of attempts it took to guess the number.
     * @param elapsedTime   The formatted elapsed time for the game round.
     */
    public void displayWinningMessage(int winningNumber, int attemptsUsed, String elapsedTime) {
        displayMessage(String.format("""
                        Congratulations! It was %d! You guessed it in %s, and it took
                        you %s.
                        """,
                winningNumber,
                getFormattedAttempts(attemptsUsed),
                elapsedTime));
    }

    /**
     * Displays a message prompting the user to try to again with the remaining
     * attempts.
     *
     * @param attemptsLeft The number of attempts left.
     */
    public void displayRetryMessage(int attemptsLeft) {
        displayMessage(String.format("""
                        You have %s left. Try again:""",
                getFormattedAttempts(attemptsLeft)));
    }

    /**
     * Displays a game-over message when the player runs out of attempts.
     *
     * @param winningNumber The number that was to be guessed.
     */
    public void displayGameOverMessage(int winningNumber) {
        displayMessage(String.format("""
                        Sorry, you have run out of attempts. The correct number was %d.""",
                winningNumber));
    }

    /**
     * Asks if the user wants to play another round.
     */
    public boolean askToPlayAgain() {
        displayMessage("""
                Do you want to play again? (yes/no):""");

        while (true) {
            String response = promptInput().toLowerCase();

            switch (response) {
                case "yes":
                    return true;
                case "no":
                    return false;
                default:
                    displayMessage("""
                            Invalid response. Please type 'yes' or 'no':""");
            }
        }
    }

    /**
     * Displays a goodbye message and closes the user interface.
     */
    public void close() {
        displayMessage("""
                Thank you for playing. Bye!""");
        scanner.close();
    }
}
