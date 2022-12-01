package captain.cybot.adventure.backend.exception;

public class PrerequsiteNotMetException extends Exception {
    public PrerequsiteNotMetException() {
        super();
    }

    public PrerequsiteNotMetException(String message) {
        super(message);
    }

    public PrerequsiteNotMetException(String message, Throwable cause) {
        super(message, cause);
    }
}
