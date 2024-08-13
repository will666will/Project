package exception;

/**
 * Thrown to indicate that the password provided for authentication is incorrect.
 *
 * @version 1.0
 */
public class IncorrectPasswordException extends Exception {

    /**
     * Constructs an IncorrectPasswordException with the specified detail message.
     *
     * @param message the detail message
     */
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
