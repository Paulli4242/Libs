package xyz.dc_stats.database.statements;

import java.util.concurrent.CompletableFuture;

public class FromStatement<T> {

    private String table;
    private WhereStatement<T> next;
    private ProcessableStatement<T> start;

    FromStatement(ProcessableStatement<T> start, String table){
        this.table = table;
        this.start = start;
    }

    public String getTable() {
        return table;
    }
    public WhereStatement<T> next(){
        return next;
    }
    public WhereStatement<T> where() {
        return (next = new WhereStatement(start,true));
    }

    public CompletableFuture<T> process() {
        return start.process();
    }
}