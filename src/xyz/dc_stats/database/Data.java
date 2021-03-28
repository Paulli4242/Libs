package xyz.dc_stats.database;

import xyz.dc_stats.utils.io.ByteUtils;

public class Data implements ByteConvertable {

    byte[] data;
    public Data(byte[] data){
        this.data = data;
    }

    public byte getByte(){
        return data[0];
    }

    public short getShort(){
        return ByteUtils.bytesToShort(data);
    }
    public int getInt(){
        return ByteUtils.bytesToInt(data);
    }
    public long getLong(){
        return ByteUtils.bytesToLong(data);
    }

    public float getFloat(){
        return ByteUtils.bytesToFloat(data);
    }
    public double getDouble(){
        return ByteUtils.bytesToDouble(data);
    }
    public char getChar(){
        return ByteUtils.bytesToChar(data);
    }
    public boolean getBoolean(){
        return ByteUtils.bytesToBoolean(data);
    }
    public String getString(){
        return ByteUtils.bytesToString(data);
    }
    public <T> T get(ByteConverter<T> t){
        return t.fromBytes(data);
    }
    @Override
    public byte[] toByteArray() {
        return new byte[0];
    }

    public static Data from(byte data){
        return new Data(new byte[]{data});
    }
    public static Data from(short data){
        return new Data(ByteUtils.numberToBytes(data));
    }
    public static Data from(int data){
        return new Data(ByteUtils.numberToBytes(data));
    }
    public static Data from(long data){
        return new Data(ByteUtils.numberToBytes(data));
    }
    public static Data from(float data){
        return new Data(ByteUtils.numberToBytes(data));
    }
    public static Data from(double data){
        return new Data(ByteUtils.numberToBytes(data));
    }
    public static Data from(char data){
        return new Data(ByteUtils.charToBytes(data));
    }
    public static Data from(boolean data){
        return new Data(ByteUtils.boolToBytes(data));
    }
    public static Data from(String data){
        return new Data(ByteUtils.stringToBytes(data));
    }
    public static Data from(ByteConvertable data){
        return new Data(data.toByteArray());
    }
}