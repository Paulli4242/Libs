package xyz.dc_stats.net;

import java.net.Socket;

public abstract class NetHandler extends Thread implements AutoCloseable {

    protected PacketFormat packetFormat;
    protected IPacketReceiveHandler receiveHandler;

    public NetHandler(PacketFormat packetFormat, IPacketReceiveHandler receiveHandler){
        this.packetFormat = packetFormat;
        this.receiveHandler = receiveHandler;
    }
    protected void received(Socket socket, byte[] b){
        try{
            receiveHandler.onPacketReceived(socket, packetFormat.formatReceive(b));
        }catch (PacketFormatException e){
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        packetFormat = null;
        receiveHandler = null;
    }
}
