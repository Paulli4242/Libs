package org.ccffee.utils.io;

import java.util.Arrays;

public class Data implements ByteConvertable {

    byte[] data;
    public Data(byte[] data){
        this.data = data;
    }
    @Deprecated
    public byte getByte(){
        return data[0];
    }
    @Deprecated
    public short getShort(){
        return ByteUtils.bytesToShort(data);
    }
    @Deprecated
    public int getInt(){
        return ByteUtils.bytesToInt(data);
    }
    @Deprecated
    public long getLong(){
        return ByteUtils.bytesToLong(data);
    }
    @Deprecated
    public float getFloat(){
        return ByteUtils.bytesToFloat(data);
    }
    @Deprecated
    public double getDouble(){
        return ByteUtils.bytesToDouble(data);
    }
    @Deprecated
    public char getChar(){
        return ByteUtils.bytesToChar(data);
    }
    @Deprecated
    public boolean getBoolean(){
        return ByteUtils.bytesToBoolean(data);
    }
    @Deprecated
    public String getString(){
        return ByteUtils.bytesToString(data);
    }
    public <T> T get(FromByteConverter<T> t){
        return t.fromBytes(data);
    }
    @Override
    public byte[] toByteArray() {
        return data;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }

    @Deprecated
    public static Data from(byte data){
        return new Data(new byte[]{data});
    }
    @Deprecated
    public static Data from(short data){
        return new Data(ByteUtils.numberToBytes(data));
    }
    @Deprecated
    public static Data from(int data){
        return new Data(ByteUtils.numberToBytes(data));
    }
    @Deprecated
    public static Data from(long data){
        return new Data(ByteUtils.numberToBytes(data));
    }
    @Deprecated
    public static Data from(float data){
        return new Data(ByteUtils.numberToBytes(data));
    }
    @Deprecated
    public static Data from(double data){
        return new Data(ByteUtils.numberToBytes(data));
    }
    @Deprecated
    public static Data from(char data){
        return new Data(ByteUtils.charToBytes(data));
    }
    @Deprecated
    public static Data from(boolean data){
        return new Data(ByteUtils.boolToBytes(data));
    }
    @Deprecated
    public static Data from(String data){
        return new Data(ByteUtils.stringToBytes(data));
    }
    public static Data from(ByteConvertable data){
        return new Data(data.toByteArray());
    }
    public static <T> Data from(ToByteConverter<T> converter, T data){
        return new Data(converter.toBytes(data));
    }


}