package org.ccffee.database.statements;

import org.ccffee.database.DBHandler;

import java.util.concurrent.CompletableFuture;

/**
 *
 * Class InsertStatement represents a INSERT statement
 *
 */
public class InsertStatement {

    private IntoStatement next;
    private DBHandler handler;

    /**
     *
     * Creates a InsertStatement
     *
     * @param handler
     */
    public InsertStatement(DBHandler handler){
        this.handler = handler;
    }

    /**
     *
     * Adds a INTO statement
     *
     * @param table name of the table
     * @param columns name of the columns
     * @return
     */
    public IntoStatement into(String table, String ... columns){
        return next = new IntoStatement(this,table,columns);
    }

    /**
     *
     * Gets the next statement
     *
     * @return next statement
     */
    public IntoStatement next(){
        return next;
    }

    /**
     *
     * Processes statement
     *
     * @return CompletableFuture\<Void\>
     */
    public CompletableFuture<Void> process() {
        return handler.process(this);
    }
}
