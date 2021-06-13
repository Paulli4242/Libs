package xyz.dc_stats.database.comparison;

public interface Comparator {

    Comparator INTEGER = new IntegerComparator();
    Comparator STRING = new StringComparator();

    boolean less(byte[] current, byte[] other );
    boolean greater(byte[] current, byte[] other);



}
