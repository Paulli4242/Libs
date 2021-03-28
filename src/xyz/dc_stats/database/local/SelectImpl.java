package xyz.dc_stats.database.local;

import xyz.dc_stats.database.DBResult;
import xyz.dc_stats.database.statements.FromStatement;
import xyz.dc_stats.database.statements.SelectStatement;
import xyz.dc_stats.database.statements.WhereStatement;

import java.util.concurrent.CompletableFuture;

public class SelectImpl implements SelectStatement {

    FromImpl from;
    CompletableFuture

    @Override
    public FromStatement from(String table) {
        return (from = new FromImpl(table));
    }
    CompletableFuture<DBResult> process(){
        CompletableFuture<>
    }
}