package org.ccffee.utils.io;

public class SimpleByteConverter<T> implements ByteConverter<T> {

    private FromByteConverter<T> from;
    private ToByteConverter<T> to;

    public SimpleByteConverter(FromByteConverter<T> from, ToByteConverter<T> to){
        this.from = from;
        this.to = to;
    }


    @Override
    public T fromBytes(byte[] b) {
        return from.fromBytes(b);
    }

    @Override
    public byte[] toBytes(T t) {
        return to.toBytes(t);
    }
}
