package xyz.dc_stats.database.exception;

public class UnknownColumnException extends Exception {
    public UnknownColumnException() {
        super();
    }

    public UnknownColumnException(String message) {
        super(message);
    }

    public UnknownColumnException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownColumnException(Throwable cause) {
        super(cause);
    }

    protected UnknownColumnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
