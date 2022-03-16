package org.ccffee.database.statements;

import org.ccffee.database.comparison.Comparator;

import java.util.concurrent.CompletableFuture;

public class WhereEndStatement<T> {

    private AfterWhereEndStatement<T> next;
    private ProcessableStatement<T> start;

    WhereEndStatement(ProcessableStatement<T> start){
        this.start = start;
    }
    public WhereStatement<T> and() {
        return (WhereStatement<T>) (next = new WhereStatement(start,true));
    }
    public WhereStatement<T> or() {
        return (WhereStatement<T>) (next = new WhereStatement(start,false));
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
    public AfterWhereEndStatement<T> next(){
        return next;
    }
    public CompletableFuture<T> process() {
        return start.process();
    }
}
