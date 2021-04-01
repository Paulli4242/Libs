package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.DBHandler;

public class CreateStatement {

    private DBHandler handler;
    private CreateTableStatement table;

    public CreateStatement(DBHandler handler){
        this.handler = handler;
    }

    public CreateTableStatement table(String ... columns) {
        return table = new CreateTableStatement(this,columns);
    }
    void process() {

    }
}
