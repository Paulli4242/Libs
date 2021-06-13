package xyz.dc_stats.database.local;

public interface Table {
    String getName();
    byte[][][] getData();
    void setData(byte[][][] data);
}
