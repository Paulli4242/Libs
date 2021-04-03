package xyz.dc_stats.database.statements;

import java.util.concurrent.CompletableFuture;

interface ProcessableStatement<T> {
    CompletableFuture<T> process();
}
