import java.util.Random;
import java.util.Scanner;

/**
 * Handles the game logic and user interaction.
 */
public class Game {
    private final Random randomGenerator = new Random();
    private final Scanner scanner = new Scanner(System.in);
    private final String promptMarker = "> ";
    private final Stopwatch stopwatch = new Stopwatch();

    private boolean wantsToPlay = true;
    private Difficulty difficulty = Difficulty.UNSELECTED;

    /**
     * Starts the game by displaying a welcome message and the rules, prompting
     * the user to select a difficulty level. Once selected, handles gameplay.
     * After the game ends, the user is given the choice to play another round
     * or exit.
     */
    public void start() {
        displayWelcomeMessage();
        displayRules();

        while (wantsToPlay) {
            promptDifficultySelection();
            playGame();
            askToPlayAgain();
        }

        sayGoodbye();
        scanner.close();
    }

    /**
     * Displays a welcome message.
     */
    private void displayWelcomeMessage() {
        System.out.println("""
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
    private void displayRules() {
        System.out.println("""
                - I will think of a number between 1 and 100 and you will have to guess
                  it within a limited number of attempts.
                - With each failed guess I will give you feedback on whether the number
                  you said is higher or lower than the number I have thought of.
                """);
    }

    /**
     * Prompts the user to select a difficulty level and sets it for the
     * current game round.
     */
    private void promptDifficultySelection() {
        System.out.println("Select difficulty (easy/medium/hard):");

        while (difficulty == Difficulty.UNSELECTED) {
            System.out.print(promptMarker);
            String selection = scanner.nextLine().trim().toLowerCase();

            switch (selection) {
                case "easy":
                    difficulty = Difficulty.EASY;
                    break;
                case "medium":
                    difficulty = Difficulty.MEDIUM;
                    break;
                case "hard":
                    difficulty = Difficulty.HARD;
                    break;
                default:
                    System.out.println("Invalid difficulty. " +
                            "Please type 'easy', 'medium', or 'hard':");
            }
        }
    }

    /**
     * Runs the game loop where the user must guess a randomly generated number
     * within the limited number of attempts determined by the selected
     * difficulty level. After each guess, feedback is provided. The game loop
     * ends when the user guesses correctly or runs out of attempts.
     */
    private void playGame() {
        int winningNumber = randomGenerator.nextInt(100) + 1;
        int attemptsLeft = difficulty.getAttempts();
        int attemptsUsed = 0;
        stopwatch.start();

        System.out.printf("""
                        Ok, I have already thought of a number between 1 and 100.
                        Since you chose the %s difficulty, you have %d attempts to guess it.
                        Enter your guess:
                        """,
                difficulty.toString().toLowerCase(),
                attemptsLeft);

        boolean gameOver = false;
        while (!gameOver) {
            int playerGuess = getPlayerGuess();
            attemptsUsed++;

            if (playerGuess == winningNumber) {
                System.out.printf("""
                        Congratulations! It was %d! You guessed it in %d attempts,
                        and it took you %s.
                        """,
                        winningNumber,
                        attemptsUsed,
                        stopwatch.getFormattedElapsedTime());
                gameOver = true;
                continue;
            } else if (playerGuess > winningNumber) {
                System.out.println("Too high!");
            } else {
                System.out.println("Too low!");
            }

            attemptsLeft--;
            if (attemptsLeft > 0) {
                System.out.println("You have " + attemptsLeft +
                        " attempt(s) left. Try again:");
            } else {
                System.out.println("Sorry, you have run out of attempts. " +
                        "The correct number was " + winningNumber + ".");
                gameOver = true;
            }
        }
    }

    /**
     * Prompts the user for a guess and validates the input.
     *
     * @return The validated guess entered by the user.
     */
    private int getPlayerGuess() {
        int playerGuess = 0;
        boolean validGuess = false;

        while (!validGuess) {
            System.out.print(promptMarker);
            try {
                playerGuess = Integer.parseInt(scanner.nextLine().trim());
                if (playerGuess < 1 || playerGuess > 100) {
                    System.out.println("Invalid guess. " +
                            "Please enter a number between 1 and 100:");
                } else {
                    validGuess = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid guess. " +
                        "Please enter a valid integer:");
            }
        }

        return playerGuess;
    }

    /**
     * Asks if the user wants to play another round.
     */
    private void askToPlayAgain() {
        System.out.println("Do you want to play again? (yes/no):");

        while (true) {
            System.out.print(promptMarker);
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes")) {
                difficulty = Difficulty.UNSELECTED;
                break;
            } else if (response.equals("no")) {
                wantsToPlay = false;
                break;
            }
            System.out.println("Invalid response. " +
                    "Please type 'yes' or 'no':");
        }
    }

    /**
     * Says goodbye when the game ends.
     */
    private void sayGoodbye() {
        System.out.println("Ok. Thank you for playing. Bye!");
    }
}
