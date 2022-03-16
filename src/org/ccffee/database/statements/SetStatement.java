package org.ccffee.database.statements;

import org.ccffee.utils.io.ByteConvertable;

/**
 *
 * Class SetStatement represents a SET statement.
 *
 */
public class SetStatement {

    private ByteConvertable[] data;
    private WhereStatement<Void> next;
    private UpdateStatement start;

    SetStatement(UpdateStatement start, ByteConvertable[] data){
        this.data = data;
        this.start = start;
    }

    /**
     *
     * Gets the Data to set.
     *
     * @return the Data to set
     */
    public ByteConvertable[] getData() {
        return data;
    }

    /**
     *
     * Gets the next statement
     *
     */
    public WhereStatement<Void> next(){
        return next;
    }

    /**
     *
     * Adds a new WHERE statement.
     *
     * @return
     */
    public WhereStatement<Void> where() {
        return (next = new WhereStatement(start,true));
    }
}
