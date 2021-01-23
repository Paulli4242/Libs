package net.foreverdevs.net;

import java.net.Socket;

public interface IPacketReceiveHandler {

    void onPacketReceived(Socket socket, Packet p);
    default void onConnectionClosed(Socket socket){}
    /**
     * getsExecuted before a new Connection gets created
     *
     * @return if the Connection is allowed
     */
    default boolean onConnectionAdd(Socket socket){
        return true;
    }
}
