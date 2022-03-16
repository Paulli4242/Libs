package org.ccffee.database.local;

import org.ccffee.database.comparison.Comparator;
import org.ccffee.utils.io.ByteConvertable;

class BetweenCondition extends Condition{

    GreaterCondition greater;
    LessCondition less;

    public BetweenCondition(boolean and, boolean not, int column, ByteConvertable data1, ByteConvertable data2, Comparator comparator) {
        super(and, not, column);
        this.greater = new GreaterCondition(and,not,column,data1,comparator);
        this.less = new LessCondition(and,not,column,data2,comparator);
    }

    @Override
    public boolean is(byte[] c) {
        return greater.is(c)&&less.is(c);
    }
}
