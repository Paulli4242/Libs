package xyz.dc_stats.database.statements;

public interface JoinableStatement<T> {
    AfterJoinableStatement<T> next();

    String getTable();

    JoinStatement<T> join(JoinStatement.Type type, String table, String column1, String column2);
    default JoinStatement<T> innerJoin(String table, String column1, String column2){
        return join(JoinStatement.Type.INNER, table, column1, column2);
    }
    default JoinStatement<T> leftJoin(String table, String column1, String column2){
        return join(JoinStatement.Type.LEFT, table, column1, column2);
    }
    default JoinStatement<T> rightJoin(String table, String column1, String column2){
        return join(JoinStatement.Type.RIGHT, table, column1, column2);
    }
    default JoinStatement<T> fullJoin(String table, String column1, String column2){
        return join(JoinStatement.Type.FULL, table, column1, column2);
    }
}
