package net.foreverdevs.net;

public interface PacketFormat{

    byte[] formatSend(Packet packet);
    Packet formatReceive(byte[] bts) throws PacketFormatException;
}
