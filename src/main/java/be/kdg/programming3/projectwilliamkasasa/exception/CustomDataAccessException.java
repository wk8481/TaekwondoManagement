

package be.kdg.programming3.projectwilliamkasasa.exception;

public class CustomDataAccessException extends RuntimeException {

    public CustomDataAccessException(String message) {
        super(message);
    }

    public CustomDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
