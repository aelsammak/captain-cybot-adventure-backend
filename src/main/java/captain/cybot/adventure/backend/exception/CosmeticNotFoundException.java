package captain.cybot.adventure.backend.exception;

public class CosmeticNotFoundException extends Exception{
    public CosmeticNotFoundException() {
        super();
    }

    public CosmeticNotFoundException(String message) {
        super(message);
    }

    public CosmeticNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
