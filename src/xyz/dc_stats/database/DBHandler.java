package xyz.dc_stats.database;

import xyz.dc_stats.database.statements.*;
import xyz.dc_stats.utils.Null;

import java.util.concurrent.CompletableFuture;

public interface DBHandler {

    SelectStatement select(String ... columns);
    CreateStatement create();
    InsertStatement insert();
    UpdateStatement update(String table,String... columns);
    DeleteStatement delete();

    CompletableFuture<DBResult> process(SelectStatement select);
    CompletableFuture<Null> process(CreateTableStatement createTable);
    CompletableFuture<Null> process(InsertStatement insert);
    CompletableFuture<Null> process(UpdateStatement update);
    CompletableFuture<Null> process(DeleteStatement delete);


}
