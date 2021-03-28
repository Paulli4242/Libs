package xyz.dc_stats.database.local;

import xyz.dc_stats.database.ByteConvertable;
import xyz.dc_stats.database.statements.WhereEndStatement;
import xyz.dc_stats.database.statements.WhereStatement;

public class WhereImpl implements WhereStatement {

    WhereEndImpl where;
    boolean not = false;
    ByteConvertable[] data;
    String column;
    Method method;

    @Override
    public WhereStatement not() {
        not = true;
        return this;
    }

    @Override
    public WhereEndStatement equals(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.EQUALS;
        return (where = new WhereEndImpl());
    }

    @Override
    public WhereEndStatement less(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.LESS;
        return (where = new WhereEndImpl());
    }

    @Override
    public WhereEndStatement greater(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.GREATER;
        return (where = new WhereEndImpl());
    }

    @Override
    public WhereEndStatement lessOrEqual(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.LESS_OR_EQUALS;
        return (where = new WhereEndImpl());
    }

    @Override
    public WhereEndStatement greaterOrEqual(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.GREATER_OR_EQUALS;
        return (where = new WhereEndImpl());
    }

    @Override
    public WhereEndStatement notEqual(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.NOT_EQUAL;
        return (where = new WhereEndImpl());
    }

    @Override
    public WhereEndStatement between(String column, ByteConvertable data0, ByteConvertable data1) {
        this.column = column;
        this.data = new ByteConvertable[]{data0,data1};
        method = Method.BETWEEN;
        return (where = new WhereEndImpl());
    }

    @Override
    public WhereEndStatement in(String column, ByteConvertable... data) {
        this.column = column;
        this.data = data;
        method = Method.IN;
        return (where = new WhereEndImpl());
    }
    enum Method{
        EQUALS,
        LESS,
        GREATER,
        LESS_OR_EQUALS,
        GREATER_OR_EQUALS,
        NOT_EQUAL,
        BETWEEN,
        IN;
    }
}