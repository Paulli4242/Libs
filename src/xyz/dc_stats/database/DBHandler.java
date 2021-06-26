package xyz.dc_stats.database;

import xyz.dc_stats.database.statements.UpdateStatement;
import xyz.dc_stats.database.statements.*;

import java.util.concurrent.CompletableFuture;

/**
 *
 * Interface DBHandler is a handler for a DataBase.
 *
 */
public interface DBHandler {

    /**
     *
     * Select and retrieves columns from a database.
     *
     * @param columns to retrieve retrieved
     * @return Se
     */
    SelectStatement select(String ... columns);
    CreateStatement create();
    InsertStatement insert();
    UpdateStatement update(String table, String... columns);
    DeleteStatement delete();

    CompletableFuture<DBResult> process(SelectStatement select);
    CompletableFuture<Void> process(CreateTableStatement createTable);
    CompletableFuture<Void> process(InsertStatement insert);
    CompletableFuture<Void> process(UpdateStatement update);
    CompletableFuture<Void> process(DeleteStatement delete);


}
