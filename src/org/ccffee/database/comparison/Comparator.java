package org.ccffee.database.comparison;

/**
 * Interface Comparator compares to byte[]
 */
public interface Comparator {

    Comparator INTEGER = new IntegerComparator();
    Comparator STRING = new StringComparator();

    /**
     *
     * Checks if current is less than other
     *
     * @param current current value
     * @param other other value
     * @return true if current is less than other, false otherwise
     */
    boolean less(byte[] current, byte[] other );
    /**
     *
     * Checks if current is greater than other
     *
     * @param current current value
     * @param other other value
     * @return true if current is greater than other, false otherwise
     */
    boolean greater(byte[] current, byte[] other);


}
