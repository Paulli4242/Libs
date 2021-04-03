package xyz.dc_stats.database.local;

import xyz.dc_stats.database.ByteConvertable;

public class BetweenCondition extends Condition{

    GreaterCondition greater;
    LessCondition less;

    public BetweenCondition(boolean and, boolean not, int column, ByteConvertable data1,ByteConvertable data2) {
        super(and, not, column);
        this.greater = new GreaterCondition(and,not,column,data1);
        this.less = new LessCondition(and,not,column,data2);
    }

    @Override
    public boolean is(byte[] c) {
        return greater.is(c)&&less.is(c);
    }
}
