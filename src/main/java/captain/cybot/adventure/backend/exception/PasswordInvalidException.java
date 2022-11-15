package captain.cybot.adventure.backend.exception;

public class PasswordInvalidException extends Exception {
    public PasswordInvalidException() {
        super();
    }

    public PasswordInvalidException(String message) {
        super(message);
    }

    public PasswordInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
