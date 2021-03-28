package xyz.dc_stats.database.local;

import xyz.dc_stats.database.DBResult;
import xyz.dc_stats.database.statements.WhereEndStatement;
import xyz.dc_stats.database.statements.WhereStatement;

import java.util.concurrent.CompletableFuture;

public class WhereEndImpl implements WhereEndStatement {

    WhereImpl where;
    EndMethod method = EndMethod.NONE;

    @Override
    public WhereStatement and() {
        method = EndMethod.AND;
        return (where = new WhereImpl());
    }

    @Override
    public WhereStatement or() {
        method = EndMethod.OR;
        return (where = new WhereImpl());
    }

    @Override
    public CompletableFuture<DBResult> process() {
        return null;
    }

    enum EndMethod{
        NONE,
        AND,
        OR;
}

}
