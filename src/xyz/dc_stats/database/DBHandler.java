package xyz.dc_stats.database;

import xyz.dc_stats.database.statements.CreateStatement;
import xyz.dc_stats.database.statements.SelectStatement;

import java.util.concurrent.CompletableFuture;

public interface DBHandler {

    SelectStatement select(String ... columns);
    CreateStatement create();

    CompletableFuture<DBResult> process(SelectStatement select);
    void process(CreateStatement create);


}
