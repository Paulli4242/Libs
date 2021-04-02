package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.DBHandler;
import xyz.dc_stats.database.DBResult;

import java.util.concurrent.CompletableFuture;

public class SelectStatement implements ProcessableStatement<DBResult> {

    private FromStatement<DBResult> next;
    private DBHandler handler;
    private String[] columns;

    public SelectStatement(DBHandler handler, String[] columns){
        this.handler = handler;
        this.columns = columns;
    }
    public String[] getColumns() {
        return columns;
    }

    public FromStatement<DBResult> next() {
        return next;
    }

    public FromStatement<DBResult> from(String table) {
        return (next = new FromStatement(this, table));
    }
    public CompletableFuture<DBResult> process(){
        return handler.process(this);
    }
}