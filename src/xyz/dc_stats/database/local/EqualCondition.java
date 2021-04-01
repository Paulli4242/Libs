package xyz.dc_stats.database.local;

import xyz.dc_stats.database.ByteConvertable;

import java.util.Arrays;

public class EqualCondition extends Condition {

    byte[] data;

    public EqualCondition(boolean and, boolean not, int column, ByteConvertable data) {
        super(and, not, column);
        this.data = data.toByteArray();
    }

    @Override
    protected boolean is(byte[] c) {
        return Arrays.equals(data,c);
    }
}
