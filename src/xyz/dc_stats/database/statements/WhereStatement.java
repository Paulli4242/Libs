package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.ByteConvertable;

public interface WhereStatement {
    WhereStatement not();
    WhereEndStatement equals(String column, ByteConvertable data);
    WhereEndStatement less(String column, ByteConvertable data);
    WhereEndStatement greater(String column, ByteConvertable data);
    WhereEndStatement lessOrEqual(String column, ByteConvertable data);
    WhereEndStatement greaterOrEqual(String column, ByteConvertable data);
    WhereEndStatement notEqual(String column, ByteConvertable data);
    WhereEndStatement between(String column, ByteConvertable data0, ByteConvertable data1);
    WhereEndStatement in(String column, ByteConvertable ... data);

}
