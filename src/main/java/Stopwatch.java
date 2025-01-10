import java.time.Duration;
import java.time.Instant;

/**
 * Measures and formats elapsed time.
 */
public class Stopwatch {
    private Instant startTimestamp;

    /**
     * Starts the stopwatch by recording the current time as the start
     * timestamp.
     */
    public void start() {
        startTimestamp = Instant.now();
    }

    /**
     * Returns the elapsed time between the start timestamp and the present
     * moment in the format "X minutes and Y seconds".
     *
     * @return A formatted string representing the elapsed time.
     */
    public String getFormattedElapsedTime() {
        if (startTimestamp == null) {
            throw new IllegalStateException("ERROR: " +
                    "Stopwatch has not been started.");
        }

        Duration duration = Duration.between(startTimestamp, Instant.now());
        long minutes = duration.toMinutes();
        int seconds = duration.toSecondsPart();

        if (minutes == 0 && seconds == 0) {
            return "less than a second";
        }

        StringBuilder formattedElapsedTime = new StringBuilder();
        if (minutes > 0) {
            formattedElapsedTime.append(minutes).append(" minute");
            if (minutes > 1) {
                formattedElapsedTime.append("s");
            }
        }
        if (seconds > 0) {
            if (minutes > 0) {
                formattedElapsedTime.append(" and ");
            }
            formattedElapsedTime.append(seconds).append(" second");
            if (seconds > 1) {
                formattedElapsedTime.append("s");
            }
        }

        return formattedElapsedTime.toString();
    }
}
