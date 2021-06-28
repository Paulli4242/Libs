package xyz.dc_stats.net;

public interface Packet {

    void deserialize(byte[] data);
    byte[] serialize();

}
