package org.ccffee.utils.io;

@FunctionalInterface
public interface FromByteConverter<T> {
    T fromBytes(byte[] b);
}
