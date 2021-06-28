package xyz.dc_stats.net;

import xyz.dc_stats.utils.exceptions.ExceptionalSupplier;
import xyz.dc_stats.utils.io.ByteUtils;
import xyz.dc_stats.utils.iteration.ArrayUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Supplier;

public class IDPacketFormat implements PacketFormat {

    private PacketEntry[] packetEntry = new PacketEntry[0];
    private int idLength;

    private class PacketEntry{
        byte[] id;
        Class<?extends Packet> clazz;
        ExceptionalSupplier<?extends Packet> supplier;

        public PacketEntry(long id, Class<? extends Packet> clazz, ExceptionalSupplier<? extends Packet> supplier) {
            this.id = Arrays.copyOf(ByteUtils.numberToBytes(id),idLength);
            this.clazz = clazz;
            this.supplier = supplier;
        }

        public ExceptionalSupplier<? extends Packet> getSupplier() {
            return supplier;
        }

        public byte[] getId() {
            return id;
        }

        public Class<? extends Packet> getClazz() {
            return clazz;
        }
    }

    HashMap<Integer,Constructor<?extends Packet>> Packets = new HashMap<>();

    public <T extends Packet> void registerPacket( long id, Class<T> clazz) throws IllegalArgumentException {
        try{
            Constructor<T> c = clazz.getConstructor();
            registerPacket(id,clazz,() -> c.newInstance());
        }catch (Exception e){
            throw new IllegalArgumentException("Could not find a constructor without arguments",e);
        }
    }
    public <T extends Packet> void registerPacket(long id, Class<T> clazz, ExceptionalSupplier<T> packetSupplier){
        packetEntry = ArrayUtils.addAndExpand(packetEntry,new PacketEntry(id,clazz,packetSupplier));
    }

    private byte[] getId(Packet p){
        for(PacketEntry e : packetEntry)if(e.getClazz().isInstance(p))return e.getId();
        return null;
    }
    private PacketEntry getEntry(byte[] id){
        for(PacketEntry e : packetEntry) if(Arrays.equals(e.getId(),id))return e;
        return null;
    }

    @Override
    public byte[] serialize(Packet packet) throws UnknownPacketException {
        byte[] i = getId(packet);
        if(i==null)throw new UnknownPacketException("Unknown Packet: "+packet.getClass().getName());
        return ArrayUtils.addArrayAndExpand(packet.serialize(), i,0);
    }

    @Override
    public Packet deserialize(byte[] data) throws UnknownPacketException, PacketCreationException {
        byte[] id = Arrays.copyOf(data,idLength);
        PacketEntry e = getEntry(id);
        if(e==null)throw new UnknownPacketException("Unknown Packet with id: "+ByteUtils.bytesToLong(id));
        Packet p;
        try {
            p = e.getSupplier().get();
        }catch (Exception ex){
            throw new PacketCreationException(ex);
        }
        p.deserialize(Arrays.copyOfRange(data,idLength,data.length));
        return p;
    }



}
