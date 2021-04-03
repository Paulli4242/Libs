package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.DBHandler;
import xyz.dc_stats.utils.Null;

import java.util.concurrent.CompletableFuture;

public class DeleteStatement implements ProcessableStatement<Null> {

    private DBHandler handler;
    private FromStatement<Null> next;


    public DeleteStatement(DBHandler handler){
        this.handler = handler;
    }

    public FromStatement<Null> from(String table){
        return next = new FromStatement<>(this,table);
    }

    public FromStatement<Null> next(){
        return next;
    }
    @Override
    public CompletableFuture<Null> process() {
        return handler.process(this);
    }
}
