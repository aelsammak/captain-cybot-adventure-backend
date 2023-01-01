package captain.cybot.adventure.backend.exception;

public class PrerequisiteNotMetException extends Exception {
    public PrerequisiteNotMetException() {
        super();
    }

    public PrerequisiteNotMetException(String message) {
        super(message);
    }

    public PrerequisiteNotMetException(String message, Throwable cause) {
        super(message, cause);
    }
}
