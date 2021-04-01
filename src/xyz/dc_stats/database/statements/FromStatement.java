package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.DBResult;

import java.util.concurrent.CompletableFuture;

public class FromStatement {

    private String table;
    private SWhereStatement next;
    private SelectStatement start;

    FromStatement(SelectStatement start, String table){
        this.table = table;
        this.start = start;
    }

    public String getTable() {
        return table;
    }
    public SWhereStatement next(){
        return next;
    }
    public SWhereStatement where() {
        return (next = new SWhereStatement(start,true));
    }

    public CompletableFuture<DBResult> process() {
        return start.process();
    }
}