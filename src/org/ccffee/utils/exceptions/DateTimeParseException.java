package org.ccffee.utils.exceptions;

public class DateTimeParseException extends RuntimeException {
    public DateTimeParseException() {
        super();
    }

    public DateTimeParseException(String message) {
        super(message);
    }

    public DateTimeParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateTimeParseException(Throwable cause) {
        super(cause);
    }

    protected DateTimeParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
