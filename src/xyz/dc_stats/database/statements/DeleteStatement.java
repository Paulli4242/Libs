package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.DBHandler;

import java.util.concurrent.CompletableFuture;

public class DeleteStatement implements ProcessableStatement<Void> {

    private DBHandler handler;
    private FromStatement<Void> next;


    public DeleteStatement(DBHandler handler){
        this.handler = handler;
    }

    public FromStatement<Void> from(String table){
        return next = new FromStatement<>(this,table);
    }

    public FromStatement<Void> next(){
        return next;
    }
    @Override
    public CompletableFuture<Void> process() {
        return handler.process(this);
    }
}
