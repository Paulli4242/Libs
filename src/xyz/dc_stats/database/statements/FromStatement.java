package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.DBResult;

import java.util.concurrent.CompletableFuture;

public interface FromStatement {
    WhereStatement where();
    CompletableFuture<DBResult> process();

}
