package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.ByteConvertable;
import xyz.dc_stats.database.DBResult;

import java.util.concurrent.CompletableFuture;

public class SWhereStatement {

    private SWhereEndStatement next;
    private SelectStatement start;
    private boolean not = false;
    private boolean and;
    private ByteConvertable[] data;
    private String column;
    private Method method;
    
    SWhereStatement(SelectStatement start,boolean and){
        this.start = start;
        this.and = and;
    }

    public SWhereStatement not() {
        not = true;
        return this;
    }

    public SWhereEndStatement equal(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.EQUAL;
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
        method = Method.LESS_OR_EQUAL;
        return (next = new SWhereEndStatement(start));
    }


    public SWhereEndStatement greaterOrEqual(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.GREATER_OR_EQUAL;
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
    public SWhereEndStatement next(){
        return next;
    }
    public boolean isNot() {
        return not;
    }

    public boolean isAnd() {
        return and;
    }

    public ByteConvertable[] getData() {
        return data;
    }
    public String getColumn() {
        return column;
    }
    public Method getMethod() {
        return method;
    }

    public enum Method{
        EQUAL,
        LESS,
        GREATER,
        LESS_OR_EQUAL,
        GREATER_OR_EQUAL,
        NOT_EQUAL,
        BETWEEN,
        IN;
    }
}