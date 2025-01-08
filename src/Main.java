import java.util.Random;
import java.util.Scanner;

/**
 * Serves as the entry point for the application.
 */
public class Main {

    /**
     * Displays the rules, prompts the user to select a difficulty level, and
     * starts the game loop where the user attempts to guess the randomly
     * generated number. After each guess, feedback is provided. The game loop
     * ends when the user guesses correctly or runs out of attempts.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        final Random randomGenerator = new Random();
        final Scanner scanner = new Scanner(System.in);
        final String promptMarker = "> ";

        System.out.println("""
                ************************************************************************
                *                                                                      *
                *                   Welcome to Number Guessing Game!                   *
                *                                                                      *
                ************************************************************************
                
                - I will think of a number between 1 and 100 and you will have to guess
                  it within a limited number of attempts.
                - With each failed guess I will give you feedback on whether the number
                  you said is higher or lower than the number I have thought of.
                """);

        System.out.println("Select difficulty (easy/medium/hard):");
        String difficulty = "";
        int attemptsLeft = 0;

        while (attemptsLeft == 0) {
            System.out.print(promptMarker);
            difficulty = scanner.nextLine().trim().toLowerCase();

            switch (difficulty) {
                case "easy":
                    attemptsLeft = 8;
                    break;
                case "medium":
                    attemptsLeft = 6;
                    break;
                case "hard":
                    attemptsLeft = 4;
                    break;
                default:
                    System.out.println("Invalid difficulty. " +
                            "Please type 'easy', 'medium', or 'hard':");
                    break;
            }
        }

        int winningNumber = randomGenerator.nextInt(100) + 1;
        System.out.printf("""
                        Ok, I have already thought of a number between 1 and 100.
                        Since you chose the %s difficulty, you have %d attempts to guess it.
                        Enter your guess:
                        """,
                difficulty, attemptsLeft);

        int attemptsUsed = 0;
        boolean gameOver = false;
        while (!gameOver) {
            System.out.print(promptMarker);
            int playerGuess;

            try {
                playerGuess = Integer.parseInt(scanner.nextLine().trim());
                if (playerGuess < 1 || playerGuess > 100) {
                    System.out.println("Invalid input. " +
                            "Please enter a number between 1 and 100:");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. " +
                        "Please enter a valid integer:");
                continue;
            }

            attemptsUsed++;
            attemptsLeft--;

            if (playerGuess == winningNumber) {
                System.out.println("Congratulations! It was " +
                        winningNumber + "! You guessed it in " +
                        attemptsUsed + " attempts.");
                break;
            } else if (playerGuess > winningNumber) {
                System.out.println("Too high! ");
            } else {
                System.out.println("Too low! ");
            }

            if (attemptsLeft > 0) {
                System.out.println("You have " + attemptsLeft +
                        " attempt(s) left. Try again:");
            } else {
                System.out.println("Sorry, you have run out of " +
                        "attempts. The correct number was " +
                        winningNumber + ".");
                gameOver = true;
            }
        }

        System.out.println("Thank you for playing. Bye!");
        scanner.close();
    }
}
