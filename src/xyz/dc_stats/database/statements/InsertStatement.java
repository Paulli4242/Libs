package xyz.dc_stats.database.statements;

public class InsertStatement {

    private IntoStatement next;

    public IntoStatement into(String table, String ... columns){
        return next = new IntoStatement(table,columns);
    }

    public IntoStatement next(){
        return next;
    }
}
