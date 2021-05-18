package xyz.dc_stats.database.statements;

import xyz.dc_stats.utils.io.ByteConvertable;

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
    public ValuesStatement values(ByteConvertable...values){
        return next = new ValuesStatement(start,new ByteConvertable[][]{values});
    }
    public ValuesStatement values(ByteConvertable[]...values){
        return  next = new ValuesStatement(start,values);
    }
    public ValuesStatement next(){
        return  next;
    }
    public String getTable() {
        return table;
    }
    public String[] getColumns() {
        return columns;
    }


}
