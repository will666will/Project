package exception;

/**
 * Thrown to indicate that a line in the data file is invalid.
 *
 * @version 1.0
 */
public class InvalidLineException extends Exception {

    /**
     * Constructs an InvalidLineException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidLineException(String message) {
        super(message);
    }
}
