package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.DBHandler;
import xyz.dc_stats.database.DBResult;
import xyz.dc_stats.utils.io.ByteUtils;

import java.util.concurrent.CompletableFuture;

public class SelectStatement {

    private FromStatement next;
    private DBHandler handler;
    private String[] columns;

    public SelectStatement(DBHandler handler, String[] columns){
        this.handler = handler;
        this.columns = columns;
    }
    public String[] getColumns() {
        return columns;
    }

    public FromStatement next() {
        return next;
    }

    public FromStatement from(String table) {
        return (next = new FromStatement(this, table));
    }
    CompletableFuture<DBResult> process(){
        return null;
    }
}