package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.ByteConvertable;
import xyz.dc_stats.utils.Null;

import java.util.concurrent.CompletableFuture;

public class ValuesStatement {

    private ByteConvertable[][] values;
    private InsertStatement start;

    ValuesStatement(InsertStatement start, ByteConvertable[][] values){
        this.values = values;
        this.start = start;
    }
    public ByteConvertable[][] getValues() {
        return values;
    }
    public CompletableFuture<Null> process(){
        return start.process();
    }
}
