package at.ac.tuwien.inso.peso.exception;

public class NotEnoughSpaceException extends RuntimeException {
    public NotEnoughSpaceException() {
        super();
    }

    public NotEnoughSpaceException(String message) {
        super(message);
    }

    public NotEnoughSpaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughSpaceException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughSpaceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
