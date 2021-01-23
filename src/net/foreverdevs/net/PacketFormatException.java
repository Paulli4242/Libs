package net.foreverdevs.net;

public class PacketFormatException extends Exception {
    public PacketFormatException() {
    }

    public PacketFormatException(String message) {
        super(message);
    }

    public PacketFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public PacketFormatException(Throwable cause) {
        super(cause);
    }
}
