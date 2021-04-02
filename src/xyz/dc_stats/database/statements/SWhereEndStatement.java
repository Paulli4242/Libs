package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.DBResult;

import java.util.concurrent.CompletableFuture;

public class SWhereEndStatement {

    private SWhereStatement next;
    private SelectStatement start;

    SWhereEndStatement(SelectStatement start){
        this.start = start;
    }
    public SWhereStatement and() {
        return (next = new SWhereStatement(start,true));
    }
    public SWhereStatement or() {
        return (next = new SWhereStatement(start,false));
    }
    public SWhereStatement next(){
        return next;
    }
    public CompletableFuture<DBResult> process() {
        return start.process();
    }
}
