package captain.cybot.adventure.backend.exception;

public class UsernameAndEmailDontMatchException extends Exception{

    public UsernameAndEmailDontMatchException() {
        super();
    }


    public UsernameAndEmailDontMatchException(String message) {
        super(message);
    }


    public UsernameAndEmailDontMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
