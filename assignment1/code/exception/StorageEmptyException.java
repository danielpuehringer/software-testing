package at.ac.tuwien.inso.peso.exception;

public class StorageEmptyException extends RuntimeException {
    public StorageEmptyException() {
        super();
    }

    public StorageEmptyException(String message) {
        super(message);
    }

    public StorageEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageEmptyException(Throwable cause) {
        super(cause);
    }

    protected StorageEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
