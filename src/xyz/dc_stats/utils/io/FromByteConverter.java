package xyz.dc_stats.utils.io;

@FunctionalInterface
public interface FromByteConverter<T> {
    T fromBytes(byte[] b);
}
