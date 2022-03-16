package org.ccffee.utils.io;

@FunctionalInterface
public interface ToByteConverter<T> {
    byte[] toBytes(T t);
}
