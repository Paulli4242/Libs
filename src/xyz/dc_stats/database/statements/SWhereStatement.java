package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.ByteConvertable;

public class SWhereStatement {

    SWhereEndStatement next;
    SelectStatement start;
    boolean not = false;
    ByteConvertable[] data;
    String column;
    Method method;
    
    SWhereStatement(SelectStatement start){
        this.start = start;
    }

    public SWhereStatement not() {
        not = true;
        return this;
    }

    public SWhereEndStatement equals(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.EQUALS;
        return (next = new SWhereEndStatement(start));
    }

    public SWhereEndStatement less(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.LESS;
        return (next = new SWhereEndStatement(start));
    }

    public SWhereEndStatement greater(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.GREATER;
        return (next = new SWhereEndStatement(start));
    }


    public SWhereEndStatement lessOrEqual(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.LESS_OR_EQUALS;
        return (next = new SWhereEndStatement(start));
    }


    public SWhereEndStatement greaterOrEqual(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.GREATER_OR_EQUALS;
        return (next = new SWhereEndStatement(start));
    }


    public SWhereEndStatement notEqual(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.NOT_EQUAL;
        return (next = new SWhereEndStatement(start));
    }


    public SWhereEndStatement between(String column, ByteConvertable data0, ByteConvertable data1) {
        this.column = column;
        this.data = new ByteConvertable[]{data0,data1};
        method = Method.BETWEEN;
        return (next = new SWhereEndStatement(start));
    }

    public SWhereEndStatement in(String column, ByteConvertable... data) {
        this.column = column;
        this.data = data;
        method = Method.IN;
        return (next = new SWhereEndStatement(start));
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