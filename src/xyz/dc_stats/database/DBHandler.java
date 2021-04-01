package xyz.dc_stats.database;

import xyz.dc_stats.database.statements.CreateStatement;
import xyz.dc_stats.database.statements.CreateTableStatement;
import xyz.dc_stats.database.statements.SelectStatement;
import xyz.dc_stats.utils.Null;

import java.util.concurrent.CompletableFuture;

public interface DBHandler {

    SelectStatement select(String ... columns);
    CreateStatement create();

    CompletableFuture<DBResult> process(SelectStatement select);
    CompletableFuture<Null> process(CreateTableStatement createTable);


}
