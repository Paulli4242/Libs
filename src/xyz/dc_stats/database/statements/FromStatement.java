package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.comparison.Comparator;

import java.util.concurrent.CompletableFuture;

public class FromStatement<T> implements JoinableStatement<T> {

    private String table;
    private AfterJoinableStatement<T> next;
    protected ProcessableStatement<T> start;

    FromStatement(ProcessableStatement<T> start, String table){
        this.table = table;
        this.start = start;
    }

    public String getTable() {
        return table;
    }
    public AfterJoinableStatement<T> next(){
        return next;
    }

    @Override
    public JoinStatement<T> join(JoinStatement.Type type,String table, String column1, String column2) {
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

    public CompletableFuture<T> process() {
        return start.process();
    }
}