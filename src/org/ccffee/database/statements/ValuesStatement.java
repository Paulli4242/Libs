package org.ccffee.database.statements;

import org.ccffee.utils.io.ByteConvertable;

import java.util.concurrent.CompletableFuture;

/**
 *
 * Class ValuesStatement represents a VALUES statement
 *
 */
public class ValuesStatement {

    private ByteConvertable[][] values;
    private InsertStatement start;

    ValuesStatement(InsertStatement start, ByteConvertable[][] values){
        this.values = values;
        this.start = start;
    }

    /**
     *
     * Gets the values.
     *
     */
    public ByteConvertable[][] getValues() {
        return values;
    }
    public CompletableFuture<Void> process(){
        return start.process();
    }
}
