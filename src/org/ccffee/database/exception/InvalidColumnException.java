package org.ccffee.database.exception;

/**
 *
 * Exception thrown when there is a invalid column.
 *
 */
public class InvalidColumnException extends Exception {

    public InvalidColumnException() {
        super();
    }

    public InvalidColumnException(String message) {
        super(message);
    }

    public InvalidColumnException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidColumnException(Throwable cause) {
        super(cause);
    }

    protected InvalidColumnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
