package org.ccffee.database.exception;

/**
 *
 * Exception thrown when a Table is invalid or already created.
 *
 */
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
