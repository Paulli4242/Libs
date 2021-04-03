package xyz.dc_stats.database;

public class DBResult {

    private String[] columns;
    private Data[][] data;

    public DBResult(String[] columns,Data[][] data){
        this.columns = columns;
        this.data = data;
    }


    public boolean isOneField(){
        return data.length==1&&data[0].length==1;
    }
    public boolean isOneLine(){
        return data.length == 1;
    }
    public boolean isTable(){
        return data.length>1;
    }
    public Data[][] getTable(){
        return data;
    }


}
