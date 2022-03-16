package org.ccffee.database.statements;

/**
 *
 * Class FromDeleteStatement represents a FROM statement after a DELETE statement.
 *
 * @param <T>
 */
public class FromDeleteStatement<T> extends FromStatement<T> {
    FromDeleteStatement(ProcessableStatement<T> start, String table) {
        super(start, table);
    }

    /**
     *
     * Gets the next statement
     *
     * @return the next statement
     */
    @Override
    public WhereStatement<T> next() {
        return (WhereStatement<T>) super.next();
    }

    /**
     *
     * Blocked Method
     *
     * @throws IllegalStateException because you can not join a DELETE statement
     *
     */
    @Override
    public JoinStatement<T> join(JoinStatement.Type type,String table, String column1, String column2) {
        throw new IllegalStateException(start.getClass().getSimpleName()+" does not support inner joins.");
    }
}
