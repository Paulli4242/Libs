package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.DBResult;

import java.util.concurrent.CompletableFuture;

public interface WhereEndStatement {
    WhereStatement and();
    WhereStatement or();
    CompletableFuture<DBResult> process();
}
