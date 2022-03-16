package org.ccffee.database.statements;

import org.ccffee.database.DBHandler;

import java.util.concurrent.CompletableFuture;

public class DeleteStatement implements ProcessableStatement<Void> {

    private DBHandler handler;
    private FromDeleteStatement<Void> next;


    public DeleteStatement(DBHandler handler){
        this.handler = handler;
    }

    public FromDeleteStatement<Void> from(String table){
        return next = new FromDeleteStatement<>(this,table);
    }

    public FromDeleteStatement<Void> next(){
        return next;
    }
    @Override
    public CompletableFuture<Void> process() {
        return handler.process(this);
    }
}
