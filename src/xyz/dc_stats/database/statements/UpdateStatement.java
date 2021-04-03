package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.ByteConvertable;
import xyz.dc_stats.database.DBHandler;
import xyz.dc_stats.utils.Null;

import java.util.concurrent.CompletableFuture;

public class UpdateStatement implements ProcessableStatement<Null> {

    private String table;
    private String[] columns;
    private SetStatement next;
    private DBHandler handler;

    public UpdateStatement(DBHandler handler, String table,String[] columns){
        this.table = table;
        this.columns = columns;
        this.handler = handler;
    }
    public SetStatement set(ByteConvertable ... data){
        return next = new SetStatement(this,data);
    }
    public SetStatement next(){
        return next;
    }
    public String getTable() {
        return table;
    }
    public String[] getColumns() {
        return columns;
    }

    @Override
    public CompletableFuture<Null> process() {
        return handler.process(this);
    }
}
