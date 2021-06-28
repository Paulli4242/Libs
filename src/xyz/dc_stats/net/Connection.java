package xyz.dc_stats.net;

import java.nio.channels.SocketChannel;

public class Connection {

    private SocketChannel channel;
    private ConnectionListener listener;
    private PacketFormat packetFormat;

    public Connection(SocketChannel channel){
        this.channel = channel;
    }

    public void sendPacket(Packet packet){
        
    }

}
