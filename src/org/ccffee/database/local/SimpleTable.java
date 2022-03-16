package org.ccffee.database.local;

import org.ccffee.utils.iteration.ArrayUtils;

/**
 *
 * Class SimpleTable is a Table used by the DB.
 *
 */
public class SimpleTable implements Table {

    private byte[][][] data;
    private String name;

    /**
     *
     * Creates a SimpleTable
     *
     * @param name
     */

    SimpleTable(String name){
        this(name,new byte[0][][]);
    }

    /**
     *
     * Creates a SimpleTable
     *
     * @param name
     * @param data
     */
    SimpleTable(String name,byte[][][] data){
        this.data = data;
        this.name = name;
    }

    /**
     *
     * Gets the name.
     *
     * @return the name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     *
     * Gets the data
     *
     * @return the data
     */
    @Override
    public byte[][][] getData() {
        return data;
    }

    /**
     *
     * Sets the data.
     *
     * @param data
     */
    public void setData(byte[][][] data) {
        this.data = data;
    }

    /**
     *
     * Adds a new Line.
     *
     * @param line
     */
    public void addLine(byte[][] line){
        data = ArrayUtils.addAndExpand(data,line);
    }

}
