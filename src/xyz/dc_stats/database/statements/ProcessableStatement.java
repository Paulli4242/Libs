package xyz.dc_stats.database.statements;

import java.util.concurrent.CompletableFuture;

/**
 *
 * interface ProcessableStatement represents processable statements.
 *
 */
interface ProcessableStatement<T> {
    /**
     *
     * Processes a statement
     *
     * @return
     */
    CompletableFuture<T> process();
}
