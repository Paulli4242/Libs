package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.ByteConvertable;

public class IntoStatement {
    private String table;
    private String[] columns;
    private ValuesStatement next;

    IntoStatement(String table, String[] columns){
        this.table = table;
        this.columns  = columns;
    }
    public ValuesStatement values(ByteConvertable...values){
        return next = new ValuesStatement(new ByteConvertable[][]{values});
    }
    public ValuesStatement values(ByteConvertable[]...values){
        return  next = new ValuesStatement(values);
    }

    public String getTable() {
        return table;
    }
    public String[] getColumns() {
        return columns;
    }
}
