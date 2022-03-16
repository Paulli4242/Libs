package org.ccffee.database.statements;

import org.ccffee.database.DBHandler;
import org.ccffee.database.DBResult;

import java.util.concurrent.CompletableFuture;

/**
 *
 * Class SelectStatement represents a SELECT statement
 *
 */
public class SelectStatement implements ProcessableStatement<DBResult> {

    private FromStatement<DBResult> next;
    private DBHandler handler;
    private String[] columns;

    /**
     *
     * Creates a SelectStatement
     *
     * @param handler
     * @param columns
     */
    public SelectStatement(DBHandler handler, String[] columns){
        this.handler = handler;
        this.columns = columns;
    }

    /**
     *
     * Gets the names of the columns.
     *
     * @return the names of the columns
     */
    public String[] getColumns() {
        return columns;
    }

    /**
     *
     * Gets the next statement
     *
     * @return the next statement
     */
    public FromStatement<DBResult> next() {
        return next;
    }

    /**
     *
     * Adds a new FROM statement
     *
     * @param table name of the table
     * @return a new FromStatement
     */
    public FromStatement<DBResult> from(String table) {
        return (next = new FromStatement(this, table));
    }

    /**
     *
     * Processes the statement.
     *
     */
    public CompletableFuture<DBResult> process(){
        return handler.process(this);
    }
}