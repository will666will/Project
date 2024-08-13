package exception;

/**
 * Thrown to indicate that the format of the data is invalid.
 *
 * @version 1.0
 */
public class InvalidFormatException extends Exception {

    /**
     * Constructs an InvalidFormatException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidFormatException(String message) {
        super(message);
    }
}
