package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.ByteConvertable;

public class SetStatement {

    private ByteConvertable[] data;
    private WhereStatement<Void> next;
    private UpdateStatement start;

    SetStatement(UpdateStatement start, ByteConvertable[] data){
        this.data = data;
        this.start = start;
    }

    public ByteConvertable[] getData() {
        return data;
    }

    public WhereStatement<Void> next(){
        return next;
    }
    public WhereStatement<Void> where() {
        return (next = new WhereStatement(start,true));
    }
}
