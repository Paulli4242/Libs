package xyz.dc_stats.database.statements;

import java.util.concurrent.CompletableFuture;

public class WhereEndStatement<T> {

    private WhereStatement<T> next;
    private ProcessableStatement<T> start;

    WhereEndStatement(ProcessableStatement<T> start){
        this.start = start;
    }
    public WhereStatement<T> and() {
        return (next = new WhereStatement(start,true));
    }
    public WhereStatement<T> or() {
        return (next = new WhereStatement(start,false));
    }
    public WhereStatement<T> next(){
        return next;
    }
    public CompletableFuture<T> process() {
        return start.process();
    }
}
