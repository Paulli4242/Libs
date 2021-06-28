package xyz.dc_stats.net;

public class UnknownPacketException extends Exception{
    public UnknownPacketException() {
        super();
    }

    public UnknownPacketException(String message) {
        super(message);
    }

    public UnknownPacketException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownPacketException(Throwable cause) {
        super(cause);
    }

    protected UnknownPacketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
