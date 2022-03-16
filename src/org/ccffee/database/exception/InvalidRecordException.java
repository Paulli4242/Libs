package org.ccffee.database.exception;

/**
 *
 * Exception thrown when a record is invalid.
 *
 */
public class InvalidRecordException extends Exception{
    public InvalidRecordException() {
        super();
    }

    public InvalidRecordException(String message) {
        super(message);
    }

    public InvalidRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRecordException(Throwable cause) {
        super(cause);
    }

    protected InvalidRecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
