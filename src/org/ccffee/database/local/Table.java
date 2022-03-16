package org.ccffee.database.local;

public interface Table {
    /**
     *
     * Gets the name.
     *
     * @return the name.
     */
    String getName();
    /**
     *
     * Gets the data
     *
     * @return the data
     */
    byte[][][] getData();
    /**
     *
     * Sets the data.
     *
     * @param data
     */
    void setData(byte[][][] data);
}
