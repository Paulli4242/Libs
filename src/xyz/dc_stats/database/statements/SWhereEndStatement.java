package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.DBResult;

import java.util.concurrent.CompletableFuture;

public class SWhereEndStatement {

    private SWhereStatement next;
    private boolean and;
    private SelectStatement start;

    SWhereEndStatement(SelectStatement start){
        this.start = start;
    }

    public SWhereStatement and() {
        and = true;
        return (next = new SWhereStatement(start));
    }

    public SWhereStatement or() {
        and = false;
        return (next = new SWhereStatement(start));
    }
}
