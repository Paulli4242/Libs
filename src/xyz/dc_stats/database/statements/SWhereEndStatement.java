package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.DBResult;

import java.util.concurrent.CompletableFuture;

public class SWhereEndStatement {

    SWhereStatement next;
    EndMethod method = EndMethod.NONE;
    SelectStatement start;
    SWhereEndStatement(SelectStatement start){
        this.start = start;
    }

    public SWhereStatement and() {
        method = EndMethod.AND;
        return (next = new SWhereStatement(start));
    }

    public SWhereStatement or() {
        method = EndMethod.OR;
        return (next = new SWhereStatement(start));
    }

    public CompletableFuture<DBResult> process() {
        return start.process();
    }

    enum EndMethod{
        NONE,
        AND,
        OR;
}

}
