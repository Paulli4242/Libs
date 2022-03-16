package org.ccffee.database.statements;

import org.ccffee.utils.io.ByteConvertable;

/**
 *
 * Class IntoStatement represents a INTO statement
 *
 */
public class IntoStatement {
    private String table;
    private String[] columns;
    private ValuesStatement next;
    private InsertStatement start;

    IntoStatement(InsertStatement start,String table, String[] columns){
        this.start = start;
        this.table = table;
        this.columns  = columns;
    }

    /**
     *
     * Adds a VALUES statement with one record
     *
     * @param values one record
     * @return the ValuesStatement
     */
    public ValuesStatement values(ByteConvertable...values){
        return next = new ValuesStatement(start,new ByteConvertable[][]{values});
    }
    /**
     *
     * Adds a VALUES statement with more record
     *
     * @param values more records
     * @return the ValuesStatement
     */
    public ValuesStatement values(ByteConvertable[]...values){
        return  next = new ValuesStatement(start,values);
    }

    /**
     *
     * Gets the next statement
     *
     * @return the next statement
     */
    public ValuesStatement next(){
        return  next;
    }

    /**
     *
     * Gets the name of the table.
     *
     * @return the name of the table
     */
    public String getTable() {
        return table;
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


}
