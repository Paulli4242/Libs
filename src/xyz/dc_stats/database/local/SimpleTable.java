package xyz.dc_stats.database.local;

import xyz.dc_stats.utils.iteration.ArrayUtils;

public class SimpleTable implements Table {

    private byte[][][] data;
    private String name;

    SimpleTable(String name){
        this(name,new byte[0][][]);
    }
    SimpleTable(String name,byte[][][] data){
        this.data = data;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte[][][] getData() {
        return data;
    }
    public void setData(byte[][][] data) {
        this.data = data;
    }

    public void addLine(byte[][] line){
        data = ArrayUtils.addAndExpand(data,line);
    }

}
