package org.ccffee.database.statements;

import org.ccffee.database.DBHandler;

import java.util.concurrent.CompletableFuture;

/**
 *
 * Class CreateTableStatement represents a TABLE statement after a CREATE statement
 *
 */
public class CreateTableStatement {
    String[] columns;
    String name;
    DBHandler start;

    CreateTableStatement(DBHandler start,String name, String[] columns){
        this.columns = columns;
        this.start = start;
        this.name = name;
    }

    /**
     *
     * Gets the name of the table
     *
     * @return name of the table
     */
    public String getName() {
        return name;
    }

    /**
     *
     * Gets the names of the columns
     *
     * @return the names of the columns
     */
    public String[] getColumns() {
        return columns;
    }

    /**
     *
     * Processes the CreateTableStatement
     *
     * @return
     */
    public CompletableFuture<Void> process(){
        return start.process(this);
    }
}
