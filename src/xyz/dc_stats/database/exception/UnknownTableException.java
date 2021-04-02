package xyz.dc_stats.database.exception;

public class UnknownTableException extends Exception {
    public UnknownTableException() {
        super();
    }

    public UnknownTableException(String message) {
        super(message);
    }

    public UnknownTableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownTableException(Throwable cause) {
        super(cause);
    }

    protected UnknownTableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
