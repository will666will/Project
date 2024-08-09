package exception;

/**
 * Thrown to indicate that a specified resource was not found.
 *
 * @version 1.0
 */
public class NotFoundException extends Exception {

    /**
     * Constructs a NotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public NotFoundException(String message) {
        super(message);
    }
}
