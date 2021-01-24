package xyz.dc_stats.net;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerNetHandler extends NetHandler {
    private ServerSocket serverSocket;
    private boolean isRunning;
    private ServerConnectionHandler connectionHandler;

    public ServerNetHandler(PacketFormat packetFormat, IPacketReceiveHandler receiveHandler,int port) throws IOException {
        this(packetFormat,receiveHandler,port,false);
    }
    public ServerNetHandler(PacketFormat packetFormat, IPacketReceiveHandler receiveHandler,int port, boolean daemon) throws IOException {
        super(packetFormat, receiveHandler);
        serverSocket = new ServerSocket(port);
        isRunning=true;
        connectionHandler = new ServerConnectionHandler(this);
        connectionHandler.setDaemon(daemon);
        connectionHandler.start();
        this.setDaemon(daemon);
        this.start();
    }
    @Override
    public void close() throws Exception {
        serverSocket = null;
        isRunning=false;
    }

    @Override
    public void run() {
        while (isRunning){
            try {
                serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean isRunning(){
        return isRunning;
    }
}
