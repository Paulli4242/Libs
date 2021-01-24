package xyz.dc_stats.net;

import xyz.dc_stats.utils.io.ByteUtils;
import xyz.dc_stats.utils.iteration.ArrayUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ClassNamePacketFormat implements PacketFormat {
    ClassLoader[] loaders;
    ClassRelation[] relations;
    public ClassNamePacketFormat(ClassLoader...loaders){
        relations = new ClassRelation[0];
        this.loaders = ArrayUtils.addAndExpand(loaders,ClassLoader.getSystemClassLoader());
    }
    protected Class<?extends Packet> getClazz(byte[] name){
        for(ClassRelation cl : relations)if(cl.isName(name))return cl.getClazz();
        return null;
    }
    protected byte[] getName(Class<?extends Packet> packet){
        for(ClassRelation cl : relations)if(cl.isClazz(packet))return cl.getName();
        return null;
    }
    @Override
    public byte[] formatSend(Packet packet) {
        return ByteUtils.pack(getName(packet.getClass()),packet.serialize());
    }
    @Override
    public Packet formatReceive(byte[] bts) throws PacketFormatException {
        byte[][] b = ByteUtils.unpack(bts);
        Class<?extends Packet> clazz = getClazz(b[0]);
        if(clazz == null){
            clazz = nameToClazz(b[0]);
            relations = ArrayUtils.addAndExpand(relations,new ClassRelation(clazz,b[0]));
        }
        try{
            Constructor<?extends Packet> c = clazz.getDeclaredConstructor();
            c.setAccessible(true);
            Packet p = c.newInstance();
            p.deserialize(b[1]);
            return p;
        }catch (NoSuchMethodException e){
            throw new PacketFormatException("missing nullery constructor",e);
        } catch (IllegalAccessException e) {
            throw new PacketFormatException("No access to the constructor",e);
        }  catch (InvocationTargetException e) {
            throw new PacketFormatException("exception thrown while constructing",e);
        } catch (InstantiationException e) {
            throw new PacketFormatException("can not be abstract",e);
        }
    }

    protected byte[] clazzToName(Class<?extends Packet> clazz){
        return ByteUtils.stringToBytes(clazz.getName());
    }
    protected Class<?extends Packet> nameToClazz(byte[] name){
        for(ClassLoader l : loaders)try{
            Class<?> c = l.loadClass(ByteUtils.bytesToString(name));
            if(Packet.class.isAssignableFrom(c))return c.asSubclass(Packet.class);
        }catch (ClassNotFoundException e){
        }
        return null;
    }

    private class ClassRelation{
        Class<?extends Packet> clazz;
        byte[] name;
        ClassRelation(Class<?extends Packet> clazz,byte[] name){
            this.clazz = clazz;
            this.name = name;
        }
        public Class<?extends Packet> getClazz(){
            return clazz;
        }
        public byte[] getName(){
            return name;
        }
        public boolean isClazz(Class<?extends Packet> clazz){
            return this.clazz.equals(clazz);
        }
        public boolean isName(byte[] name){
            return Arrays.equals(this.name,name);
        }
    }
}
