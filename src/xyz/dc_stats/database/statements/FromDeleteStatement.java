package xyz.dc_stats.database.statements;

public class FromDeleteStatement<T> extends FromStatement<T> {
    FromDeleteStatement(ProcessableStatement<T> start, String table) {
        super(start, table);
    }

    @Override
    public WhereStatement<T> next() {
        return (WhereStatement<T>) super.next();
    }

    @Override
    public JoinStatement<T> join(JoinStatement.Type type,String table, String column1, String column2) {
        throw new IllegalStateException(start.getClass().getSimpleName()+" does not support inner joins.");
    }
}
