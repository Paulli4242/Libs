package xyz.dc_stats.utils.io;

@FunctionalInterface
public interface ToByteConverter<T> {
    byte[] toBytes(T t);
}
