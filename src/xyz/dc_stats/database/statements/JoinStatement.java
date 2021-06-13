package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.comparison.Comparator;

import java.util.concurrent.CompletableFuture;

public class JoinStatement<T> implements AfterJoinableStatement<T>,JoinableStatement<T> {

    public enum Type{
        INNER,
        LEFT,
        RIGHT,
        FULL;
    }

    private AfterJoinableStatement<T> next;
    private ProcessableStatement<T> start;
    private String table;
    private String column1;
    private String column2;
    private Type type;

    public JoinStatement(ProcessableStatement<T> start, Type type, String table, String column1, String column2) {
        this.start = start;
        this.table = table;
        this.column1 = column1;
        this.column2 = column2;
        this.type = type;
    }

    @Override
    public AfterJoinableStatement<T> next() {
        return next;
    }

    @Override
    public JoinStatement<T> join(Type type,String table, String column1, String column2) {
        return(JoinStatement<T>) (next = new JoinStatement<>(start, type,table,column1,column2));
    }
    public WhereStatement<T> where() {
        return (WhereStatement<T>) (next = new WhereStatement(start,true));
    }
    public OrderByStatement<T> orderBy(String column){
        return orderBy(column,true, Comparator.INTEGER);
    }
    public OrderByStatement<T> orderBy(String column, boolean ascending){
        return orderBy(column, ascending,Comparator.INTEGER);
    }
    public OrderByStatement<T> orderBy(String column, boolean ascending, Comparator comparator){
        return (OrderByStatement) (next=new OrderByStatement<T>(start,column,ascending,comparator));
    }

    public String getTable() {
        return table;
    }

    public String getColumn1() {
        return column1;
    }

    public String getColumn2() {
        return column2;
    }

    public Type getType() {
        return type;
    }

    public CompletableFuture<T> process() {
        return start.process();
    }

}
