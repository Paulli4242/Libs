package xyz.dc_stats.database;

@FunctionalInterface
public interface ByteConverter<T> {
    T fromBytes(byte[] b);
}
