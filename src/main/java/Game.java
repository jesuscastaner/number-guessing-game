import java.util.Random;

/**
 * Handles the core game logic.
 */
public class Game {
    private final UserInterface ui = new UserInterface();
    private final Random randomGenerator = new Random();
    private final Stopwatch stopwatch = new Stopwatch();

    /**
     * Starts the game by displaying a welcome message and the rules, prompting
     * the user to select a difficulty level. Once selected, handles gameplay.
     * After the game ends, the user is given the choice to play another round
     * or exit.
     */
    public void start() {
        ui.displayWelcomeMessage();
        ui.displayRules();

        boolean wantsToPlay = true;
        while (wantsToPlay) {
            Difficulty difficulty = ui.promptDifficultySelection();
            playRound(difficulty);
            wantsToPlay = ui.askToPlayAgain();
        }

        ui.close();
    }

    /**
     * Runs the game loop where the user must guess a randomly generated number
     * within the limited number of attempts determined by the selected
     * difficulty level. After each guess, feedback is provided. The game loop
     * ends when the user guesses correctly or runs out of attempts.
     */
    private void playRound(Difficulty difficulty) {
        int winningNumber = randomGenerator.nextInt(100) + 1;
        int attemptsLeft = difficulty.getAttempts();
        int attemptsUsed = 0;
        stopwatch.start();

        ui.displayRoundStartMessage(difficulty);

        boolean gameOver = false;
        while (!gameOver) {
            int playerGuess = ui.promptPlayerGuess();
            attemptsUsed++;

            if (playerGuess == winningNumber) {
                ui.displayWinningMessage(winningNumber, attemptsUsed,
                        stopwatch.getFormattedElapsedTime());
                gameOver = true;
                continue;
            } else if (playerGuess > winningNumber) {
                ui.displayMessage("Too high!");
            } else {
                ui.displayMessage("Too low!");
            }

            attemptsLeft--;
            if (attemptsLeft > 0) {
                ui.displayRetryMessage(attemptsLeft);
            } else {
                ui.displayGameOverMessage(winningNumber);
                gameOver = true;
            }
        }
    }
}
