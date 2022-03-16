package org.ccffee.database;

import org.ccffee.database.statements.*;

import java.util.concurrent.CompletableFuture;

/**
 *
 * Interface DBHandler is a handler for a DataBase.
 *
 */
public interface DBHandler {

    /**
     *
     * Creates a new SelectStatement.
     *
     * @param columns to retrieve
     * @return a SelectStatement
     */
    SelectStatement select(String ... columns);

    /**
     *
     * Creates a new CreateStatement.
     *
     * @return a CreateStatement
     */
    CreateStatement create();

    /**
     *
     * Creates a new InsertStatement.
     *
     * @return a InsertStatement
     */
    InsertStatement insert();

    /**
     *
     * Creates a new UpdateStatement
     *
     * @param table - name of the table.
     * @param columns to update.
     * @return a UpdateStatement
     */
    UpdateStatement update(String table, String... columns);

    /**
     *
     * Creates a new DeleteStatement
     *
     *
     * @return a DeleteStatement
     */
    DeleteStatement delete();

    CompletableFuture<DBResult> process(SelectStatement select);
    CompletableFuture<Void> process(CreateTableStatement createTable);
    CompletableFuture<Void> process(InsertStatement insert);
    CompletableFuture<Void> process(UpdateStatement update);
    CompletableFuture<Void> process(DeleteStatement delete);



}
