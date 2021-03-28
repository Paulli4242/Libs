package xyz.dc_stats.database.statements;

public class CreateStatement {
    public CreateTableStatement table(String ... columns){
        return new CreateTableStatement(this,columns);
    }
    void process(){

    }
}
