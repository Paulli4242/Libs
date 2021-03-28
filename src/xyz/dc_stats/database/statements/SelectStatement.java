package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.DBHandler;
import xyz.dc_stats.database.DBResult;

import java.util.concurrent.CompletableFuture;

public class SelectStatement {

    FromStatement next;
    DBHandler processor;

    public FromStatement from(String table) {
        return (next = new FromStatement(this, table));
    }
    CompletableFuture<DBResult> process(){
        return null;
    }
}