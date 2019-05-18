package exceptions;

public class JWBLoginException extends RuntimeException {
    public JWBLoginException(String message) {
        super(message);
    }

    public JWBLoginException(Throwable cause) {
        super(cause);
    }
}
