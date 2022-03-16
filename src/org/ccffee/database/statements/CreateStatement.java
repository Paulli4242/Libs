package org.ccffee.database.statements;

import org.ccffee.database.DBHandler;

/**
 *
 * Class witch represents a CREATE statement
 *
 */
public class CreateStatement {

    private DBHandler handler;
    private CreateTableStatement table;

    /**
     *
     * Creates a CreateStatement
     *
     * @param handler
     */
    public CreateStatement(DBHandler handler){
        this.handler = handler;
    }


    /**
     *
     * Adds a new TABLE statement.
     *
     * @param name
     * @param columns
     * @return the CreateTableStatement
     */
    public CreateTableStatement table(String name, String ... columns) {
        return table = new CreateTableStatement(handler,name,columns);
    }
}
