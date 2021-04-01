package xyz.dc_stats.database.local;

import xyz.dc_stats.database.ByteConvertable;

import java.util.Arrays;

public class InCondition extends Condition{

    private byte[][] data;

    public InCondition(boolean and, boolean not, int column, ByteConvertable[] data) {
        super(and, not, column);
        this.data = new byte[data.length][];
        for(int i = 0; i<data.length;i++)this.data[i]=data[i].toByteArray();
    }

    @Override
    public boolean is(byte[] c) {
        for(byte[] d : data)if(Arrays.equals(d,c))return true;
        return false;
    }
}