package org.ccffee.database.statements;

/**
 *
 * Interface JoinableStatement represents a statement after witch can follow a JOIN statement.
 *
 * @param <T>
 */
public interface JoinableStatement<T> {
    AfterJoinableStatement<T> next();

    String getTable();

    /**
     *
     * Adds a JOIN statement
     *
     * @param type INNER | LEFT | RIGHT | FULL
     * @param table name of the join table
     * @param column1 first column
     * @param column2 second column
     * @return the JoinStatement
     */
    JoinStatement<T> join(JoinStatement.Type type, String table, String column1, String column2);

    /**
     *
     * Adds a INNER JOIN statement
     *
     * @param table name of the join table
     * @param column1 first column
     * @param column2 second column
     * @return the JoinStatement
     */
    default JoinStatement<T> innerJoin(String table, String column1, String column2){
        return join(JoinStatement.Type.INNER, table, column1, column2);
    }
    /**
     *
     * Adds a LEFT JOIN statement
     *
     * @param table name of the join table
     * @param column1 first column
     * @param column2 second column
     * @return the JoinStatement
     */
    default JoinStatement<T> leftJoin(String table, String column1, String column2){
        return join(JoinStatement.Type.LEFT, table, column1, column2);
    }
    /**
     *
     * Adds a RIGHT JOIN statement
     *
     * @param table name of the join table
     * @param column1 first column
     * @param column2 second column
     * @return the JoinStatement
     */
    default JoinStatement<T> rightJoin(String table, String column1, String column2){
        return join(JoinStatement.Type.RIGHT, table, column1, column2);
    }
    /**
     *
     * Adds a FULL JOIN statement
     *
     * @param table name of the join table
     * @param column1 first column
     * @param column2 second column
     * @return the JoinStatement
     */
    default JoinStatement<T> fullJoin(String table, String column1, String column2){
        return join(JoinStatement.Type.FULL, table, column1, column2);
    }
}
