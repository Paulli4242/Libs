package org.ccffee.database.statements;

import org.ccffee.database.DBHandler;
import org.ccffee.utils.io.ByteConvertable;

import java.util.concurrent.CompletableFuture;

/**
 *
 * Class UpdateStatement represents a UPDATE statement
 *
 */
public class UpdateStatement implements ProcessableStatement<Void> {

    private String table;
    private String[] columns;
    private SetStatement next;
    private DBHandler handler;


    public UpdateStatement(DBHandler handler, String table,String[] columns){
        this.table = table;
        this.columns = columns;
        this.handler = handler;
    }

    /**
     *
     * Adds a new SET statement.
     *
     * @param data data to set.
     */
    public SetStatement set(ByteConvertable... data){
        return next = new SetStatement(this,data);
    }

    /**
     *
     * Gets the next statement
     *
     */
    public SetStatement next(){
        return next;
    }

    /**
     *
     * Gets the name of the table.
     *
     */
    public String getTable() {
        return table;
    }

    /**
     *
     * Gets the names of the columns.
     *
     */
    public String[] getColumns() {
        return columns;
    }

    /**
     *
     * Processes the statement
     *
     * @return
     */
    @Override
    public CompletableFuture<Void> process() {
        return handler.process(this);
    }
}
