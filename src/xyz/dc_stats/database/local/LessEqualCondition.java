package xyz.dc_stats.database.local;

import xyz.dc_stats.database.comparison.Comparator;
import xyz.dc_stats.utils.io.ByteConvertable;

import java.util.Arrays;

class LessEqualCondition extends Condition{

    private byte[] data;
    private Comparator comparator;

    public LessEqualCondition(boolean and, boolean not, int column, ByteConvertable data, Comparator comparator) {
        super(and, not, column);
        this.data = data.toByteArray();
        this.comparator = comparator;
    }

    @Override
    public boolean is(byte[] c) {
        if(Arrays.equals(c,data))return true;
        return comparator.less(c,data);
    }
}
