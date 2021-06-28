package xyz.dc_stats.net;

public interface PacketFormat {

    byte[] serialize(Packet packet) throws UnknownPacketException;
    Packet deserialize(byte[] data) throws UnknownPacketException, PacketCreationException;

}
