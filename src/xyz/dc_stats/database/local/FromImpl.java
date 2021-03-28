package xyz.dc_stats.database.local;

import xyz.dc_stats.database.DBResult;
import xyz.dc_stats.database.statements.FromStatement;
import xyz.dc_stats.database.statements.WhereStatement;

import java.util.concurrent.CompletableFuture;

public class FromImpl implements FromStatement {

    String[] table;
    WhereImpl where;

    public FromImpl(String ... table){
        this.table = table;
    }

    @Override
    public WhereStatement where() {
        return (where = new WhereImpl());
    }

    @Override
    public CompletableFuture<DBResult> process() {
        return null;
    }
}