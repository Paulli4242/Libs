package xyz.dc_stats.net;

import xyz.dc_stats.utils.iteration.ArrayUtils;

import java.io.IOException;
import java.net.Socket;

public class ClientNetHandler extends NetHandler {
    boolean isRunning;

    private int z=0;
    private boolean next = false;
    private byte[] out = new byte[0];
    private Socket socket;
    private byte[] send;

    public ClientNetHandler(PacketFormat packetFormat, IPacketReceiveHandler receiveHandler, String host, int port) throws IOException {
        this(packetFormat, receiveHandler, host, port, false);
    }
    public ClientNetHandler(PacketFormat packetFormat, IPacketReceiveHandler receiveHandler, String host, int port, boolean daemon) throws IOException {
        super(packetFormat, receiveHandler);
        this.setDaemon(daemon);
        socket = new Socket(host, port);
        this.start();
    }
    public void send(byte[] b){
        synchronized (send){
            send = b;
        }
    }
    public void run() {
        try {
            while (isRunning) {
                if (socket.getInputStream().available() > 0) {
                    byte b = (byte) socket.getInputStream().read();
                    if (z < out.length) {
                        out[z] = b;
                        z++;
                    } else {
                        if (next) {
                            z = 0;
                            out = new byte[Byte.toUnsignedInt(b)];
                        } else out = ArrayUtils.expand(out, Byte.toUnsignedInt(b));
                        next = b < (byte) 255;
                    }
                    if (z == out.length && next) received(socket,out);
                }
                synchronized (send){
                    if(send != null)socket.getOutputStream().write(send);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    @Override
    public void close() {
        isRunning = false;
    }
}
