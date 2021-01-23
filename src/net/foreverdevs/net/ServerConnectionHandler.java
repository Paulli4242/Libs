package net.foreverdevs.net;

import net.foreverdevs.utils.iteration.ArrayUtils;

import java.io.IOException;
import java.net.Socket;

class ServerConnectionHandler extends Thread{
    private Connection[] connections;
    private ServerNetHandler netHandler;
    void add(Socket socket){
        connections = ArrayUtils.addAndExpand(connections,new Connection(socket));
    }
    public ServerConnectionHandler(ServerNetHandler netHandler){
        connections = new Connection[0];
        this.netHandler = netHandler;
    }
    @Override
    public void run() {
        for(int i = 0; netHandler.isRunning();i++) {
            if(i>=connections.length){
                i=0;
                continue;
            }
            try {
                connections[i].run();
            } catch (IOException e) {
                connections = ArrayUtils.removeAndShrink(connections,i--);
            }
        }
    }
    private class Connection{
        int z=0;
        boolean next = false;
        byte[]out = new byte[0];
        private Connection(Socket socket){
            this.socket = socket;
        }

        private Socket socket;

        public void run() throws IOException {
            if(socket.getInputStream().available()>0){
                byte b = (byte) socket.getInputStream().read();
                if(z<out.length){
                    out[z]=b;
                    z++;
                }else{
                    if(next){
                        z=0;
                        out = new byte[Byte.toUnsignedInt(b)];
                    }else out= ArrayUtils.expand(out,Byte.toUnsignedInt(b));
                    next = b<(byte)255;
                }
                if(z==out.length&&next) netHandler.received(socket,out);
            }
        }

    }
}

