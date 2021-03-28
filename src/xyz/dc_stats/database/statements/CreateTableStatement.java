package xyz.dc_stats.database.statements;

public class CreateTableStatement {
    String[] columns;
    CreateStatement start;

    CreateTableStatement(CreateStatement start, String[] columns){
        this.columns = columns;
        this.start = start;
    }
    public void process(){
        start.process();
    }
}
