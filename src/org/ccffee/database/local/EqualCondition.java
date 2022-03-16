package org.ccffee.database.local;

import org.ccffee.utils.io.ByteConvertable;

import java.util.Arrays;

class EqualCondition extends Condition {

    byte[] data;

    public EqualCondition(boolean and, boolean not, int column, ByteConvertable data) {
        super(and, not, column);
        this.data = data.toByteArray();
    }

    @Override
    public boolean is(byte[] c) {
        return Arrays.equals(data,c);
    }
}
