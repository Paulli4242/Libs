package xyz.dc_stats.net;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.*;
import java.nio.channels.ServerSocketChannel;

public class ServerNetworkManager extends Thread {

    volatile boolean running;

    ServerSocketChannel channel;
    public ServerNetworkManager(int port, int backlog) throws IOException {
        channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(port),backlog);
    }

    @Override
    public void run() {
        while (running){
            try {
                channel.configureBlocking(false);
                channel.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopServer(){

    }

}
