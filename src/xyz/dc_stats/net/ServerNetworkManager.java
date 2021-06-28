package xyz.dc_stats.net;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerNetworkManager extends Thread {

    protected boolean running;
    protected ServerNetworkListener listener;
    protected ServerSocketChannel channel;

    public ServerNetworkManager(int port) throws IOException {
        channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(port),50);
    }

    public ServerNetworkManager(int port, int backlog) throws IOException {
        channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(port),backlog);
    }
    public ServerNetworkManager(int port,ServerNetworkListener listener) throws IOException {
        channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(port),50);
        this.listener = listener;
    }

    public ServerNetworkManager(int port, int backlog, ServerNetworkListener listener) throws IOException {
        channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(port),backlog);
        this.listener = listener;
    }


    @Override
    public void run() {
        while (running){
            try {
                channel.configureBlocking(false);
                SocketChannel c = channel.accept();
                if(c!=null&&listener!=null)new Thread(()->listener.onConnectionAccepted(new Connection(c))).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public synchronized void startServer() {
        running = true;
        super.start();
    }

    public synchronized void stopServer(){
        running = false;
    }

    public void setListener(ServerNetworkListener listener) {
        this.listener = listener;
    }

    public ServerNetworkListener getListener() {
        return listener;
    }
}
