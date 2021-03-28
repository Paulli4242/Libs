package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.DBResult;

import java.util.concurrent.CompletableFuture;

public class FromStatement {

    String[] table;
    SWhereStatement next;
    SelectStatement start;

    FromStatement(SelectStatement start, String ... table){
        this.table = table;
        this.start = start;
    }

    public SWhereStatement where() {
        return (next = new SWhereStatement(start));
    }

    public CompletableFuture<DBResult> process() {
        return start.process();
    }
}