package net.foreverdevs.net;

public interface Packet {
    byte[] serialize();
    void deserialize(byte[] bts);
}
