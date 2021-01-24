package xyz.dc_stats.net;

public interface Packet {
    byte[] serialize();
    void deserialize(byte[] bts);
}
