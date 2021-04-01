package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.DBHandler;

public class CreateTableStatement {
    String[] columns;
    String name;
    DBHandler start;

    CreateTableStatement(DBHandler start,String name, String[] columns){
        this.columns = columns;
        this.start = start;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String[] getColumns() {
        return columns;
    }

    public void process(){
        start.process(this);
    }
}