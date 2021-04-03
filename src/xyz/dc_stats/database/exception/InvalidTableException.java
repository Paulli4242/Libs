package xyz.dc_stats.database.exception;

public class InvalidTableException extends Exception {
    public InvalidTableException() {
        super();
    }

    public InvalidTableException(String message) {
        super(message);
    }

    public InvalidTableException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTableException(Throwable cause) {
        super(cause);
    }

    protected InvalidTableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
