package org.ccffee.database.statements;

import org.ccffee.database.comparison.Comparator;
import org.ccffee.utils.io.ByteConvertable;

public class WhereStatement<T> implements AfterJoinableStatement<T>,AfterWhereEndStatement<T> {

    private WhereEndStatement<T> next;
    private ProcessableStatement<T> start;
    private boolean not = false;
    private boolean and;
    private ByteConvertable[] data;
    private String column;
    private Method method;
    private Comparator comparator;
    
    WhereStatement(ProcessableStatement<T> start, boolean and){
        this.start = start;
        this.and = and;
    }

    public WhereStatement<T> not() {
        not = true;
        return this;
    }

    public WhereEndStatement<T> equal(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.EQUAL;
        return (next = new WhereEndStatement(start));
    }

    public WhereEndStatement<T> less(String column, ByteConvertable data, Comparator comparator) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.LESS;
        this.comparator = comparator;
        return (next = new WhereEndStatement(start));
    }
    public WhereEndStatement<T> less(String column, ByteConvertable data){
        return less(column, data,Comparator.INTEGER);
    }



    public WhereEndStatement<T> greater(String column, ByteConvertable data, Comparator comparator) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.GREATER;
        this.comparator = comparator;
        return (next = new WhereEndStatement(start));
    }
    public WhereEndStatement<T> greater(String column, ByteConvertable data){
        return greater(column, data,Comparator.INTEGER);
    }


    public WhereEndStatement<T> lessOrEqual(String column, ByteConvertable data, Comparator comparator) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.LESS_OR_EQUAL;
        this.comparator = comparator;
        return (next = new WhereEndStatement(start));
    }

    public WhereEndStatement<T> lessOrEqual(String column, ByteConvertable data){
        return lessOrEqual(column, data,Comparator.INTEGER);
    }

    public WhereEndStatement<T> greaterOrEqual(String column, ByteConvertable data, Comparator comparator) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.GREATER_OR_EQUAL;
        this.comparator = comparator;
        return (next = new WhereEndStatement(start));
    }

    public WhereEndStatement<T> greaterOrEqual(String column, ByteConvertable data){
        return greaterOrEqual(column, data,Comparator.INTEGER);
    }

    public WhereEndStatement<T> notEqual(String column, ByteConvertable data) {
        this.column = column;
        this.data = new ByteConvertable[]{data};
        method = Method.NOT_EQUAL;
        return (next = new WhereEndStatement(start));
    }


    public WhereEndStatement<T> between(String column, ByteConvertable data0, ByteConvertable data1,Comparator comparator) {
        this.column = column;
        this.data = new ByteConvertable[]{data0,data1};
        method = Method.BETWEEN;
        this.comparator = comparator;
        return (next = new WhereEndStatement(start));
    }
    public WhereEndStatement<T> between(String column, ByteConvertable data0, ByteConvertable data1){
        return between(column, data0, data1,Comparator.INTEGER);
    }


    public WhereEndStatement<T> in(String column, ByteConvertable... data) {
        this.column = column;
        this.data = data;
        method = Method.IN;
        return (next = new WhereEndStatement(start));
    }
    public WhereEndStatement<T> next(){
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
    public Comparator getComparator() {
        return comparator;
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