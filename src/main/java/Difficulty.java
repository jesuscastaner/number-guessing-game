/**
 * Represents the difficulty level of the game.
 */
public enum Difficulty {
    UNSELECTED(0),
    EASY(8),
    MEDIUM(6),
    HARD(4);

    private final int attempts;

    /**
     * Constructs a Difficulty with the provided number of attempts.
     *
     * @param attempts The number of attempts allowed for the difficulty level.
     */
    Difficulty(int attempts) {
        this.attempts = attempts;
    }

    /**
     * Returns the number of attempts allowed for the difficulty level.
     *
     * @return The number of attempts allowed for the difficulty level.
     */
    public int getAttempts() {
        return attempts;
    }
}
