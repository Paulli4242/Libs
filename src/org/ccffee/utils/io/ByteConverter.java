package org.ccffee.utils.io;

import java.awt.*;

public interface ByteConverter<T> extends FromByteConverter<T>,ToByteConverter<T> {
    ByteConverter<Byte> BYTE = ByteConverter.from(b->b[0],d->new byte[]{d});
    ByteConverter<Short> SHORT = ByteConverter.from(ByteUtils::bytesToShort, ByteUtils::numberToBytes);
    ByteConverter<Integer> INT = ByteConverter.from(ByteUtils::bytesToInt, ByteUtils::numberToBytes);
    ByteConverter<Long> LONG = ByteConverter.from(ByteUtils::bytesToLong, ByteUtils::numberToBytes);
    ByteConverter<Float> FLOAT = ByteConverter.from(ByteUtils::bytesToFloat, ByteUtils::numberToBytes);
    ByteConverter<Double> DOUBLE = ByteConverter.from(ByteUtils::bytesToDouble, ByteUtils::numberToBytes);
    ByteConverter<Character> CHAR = ByteConverter.from(ByteUtils::bytesToChar, ByteUtils::charToBytes);
    ByteConverter<Boolean> BOOLEAN = ByteConverter.from(ByteUtils::bytesToBoolean, ByteUtils::boolToBytes);
    ByteConverter<String> STRING = ByteConverter.from(ByteUtils::bytesToString, ByteUtils::stringToBytes);
    ByteConverter<Color> COLOR = ByteConverter.from(b->new Color(ByteUtils.bytesToInt(b)),c->ByteUtils.numberToBytes(c.getRGB()));
    static <T> ByteConverter<T> from(FromByteConverter<T> from, ToByteConverter<T> to){
        return new SimpleByteConverter<>(from,to);
    }
}
