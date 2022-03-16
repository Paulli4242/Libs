package org.ccffee.database;

import org.ccffee.utils.io.Data;

/**
 *
 * The Result of a SelectStatement
 *
 */
public class DBResult {

    private String[] columns;
    private Data[][] data;

    public DBResult(String[] columns,Data[][] data){
        this.columns = columns;
        this.data = data;
    }

    /**
     *
     * Checks if contains only one Field
     *
     * @return true if contains only one Field, false otherwise
     */
    public boolean containsOneField(){
        return data.length==1&&data[0].length==1;
    }

    /**
     *
     * Gets the Data of the field.
     *
     * @return Data of the field
     *
     * @throws IllegalStateException if it contains more or less then one field.
     */
    public Data getField(){
        if(!containsOneField()) throw new IllegalStateException("Contains more or less than one field.");
        return data[0][0];
    }
    /**
     *
     * Checks if contains only one record
     *
     * @return true if contains only one record, false otherwise
     */
    public boolean containsOneRecord(){
        return data.length == 1;
    }

    /**
     *
     * Gets the record.
     *
     * @return the record
     *
     * @throws IllegalStateException if it contains more or less then one record.
     */
    public Data[] getRecord(){
        if(!containsOneRecord()) throw new IllegalStateException("Contains more or less than one record.");
        return data[0];
    }

    /**
     *
     * Checks if contains more than one records
     *
     * @return true if contains more than one record, false otherwise
     */
    public boolean containsNRecords(){
        return data.length>1;
    }
    public Data[][] getTable(){
        return data;
    }

    /**
     *
     * Gets the names of the columns.
     *
     * @return names of the columns
     */
    public String[] getColumns() {
        return columns;
    }
}
