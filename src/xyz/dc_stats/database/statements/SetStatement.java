package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.ByteConvertable;
import xyz.dc_stats.utils.Null;

public class SetStatement {

    private ByteConvertable[] data;
    private WhereStatement<Null> next;
    private UpdateStatement start;

    SetStatement(UpdateStatement start, ByteConvertable[] data){
        this.data = data;
        this.start = start;
    }

    public ByteConvertable[] getData() {
        return data;
    }

    public WhereStatement<Null> next(){
        return next;
    }
    public WhereStatement<Null> where() {
        return (next = new WhereStatement(start,true));
    }
}
