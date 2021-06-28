package xyz.dc_stats.net;

public class PacketCreationException extends Exception{
    public PacketCreationException() {
        super();
    }

    public PacketCreationException(String message) {
        super(message);
    }

    public PacketCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PacketCreationException(Throwable cause) {
        super(cause);
    }

    protected PacketCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
