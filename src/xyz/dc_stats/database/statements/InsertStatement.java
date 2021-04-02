package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.DBHandler;
import xyz.dc_stats.utils.Null;

import java.util.concurrent.CompletableFuture;

public class InsertStatement {

    private IntoStatement next;
    private DBHandler handler;

    public InsertStatement(DBHandler handler){
        this.handler = handler;
    }

    public IntoStatement into(String table, String ... columns){
        return next = new IntoStatement(this,table,columns);
    }

    public IntoStatement next(){
        return next;
    }

    public CompletableFuture<Null> process() {
        return handler.process(this);
    }
}
