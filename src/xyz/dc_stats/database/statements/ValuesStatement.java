package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.ByteConvertable;

public class ValuesStatement {

    ByteConvertable[][] values;

    ValuesStatement(ByteConvertable[][] values){
        this.values = values;
    }

    public ByteConvertable[][] getValues() {
        return values;
    }
}
