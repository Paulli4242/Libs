package org.ccffee.database.statements;

import org.ccffee.database.comparison.Comparator;

import java.util.concurrent.CompletableFuture;


/**
 *
 * Class JoinStatement represents a JOIN statement
 *
 * @param <T>
 */
public class JoinStatement<T> implements AfterJoinableStatement<T>,JoinableStatement<T> {

    /**
     *
     * Enum Type describes the types of JOIN statements
     *
     */
    public enum Type{
        INNER,
        LEFT,
        RIGHT,
        FULL;
    }


    private AfterJoinableStatement<T> next;
    private ProcessableStatement<T> start;
    private String table;
    private String column1;
    private String column2;
    private Type type;

    JoinStatement(ProcessableStatement<T> start, Type type, String table, String column1, String column2) {
        this.start = start;
        this.table = table;
        this.column1 = column1;
        this.column2 = column2;
        this.type = type;
    }


    /**
     *
     * Gets the next statement
     *
     * @return the next statement
     */
    @Override
    public AfterJoinableStatement<T> next() {
        return next;
    }

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
    @Override
    public JoinStatement<T> join(Type type,String table, String column1, String column2) {
        return(JoinStatement<T>) (next = new JoinStatement<>(start, type,table,column1,column2));
    }
    /**
     *
     * Adds a WHERE statement
     *
     * @return thr WhereStatement
     */
    public WhereStatement<T> where() {
        return (WhereStatement<T>) (next = new WhereStatement(start,true));
    }
    /**
     *
     * Adds a orderBy statement
     *
     * @return the OrderByStatement
     */
    public OrderByStatement<T> orderBy(String column){
        return orderBy(column,true, Comparator.INTEGER);
    }
    /**
     *
     * Adds a orderBy statement
     *
     * @return the OrderByStatement
     */
    public OrderByStatement<T> orderBy(String column, boolean ascending){
        return orderBy(column, ascending,Comparator.INTEGER);
    }
    /**
     *
     * Adds a orderBy statement
     *
     * @return the OrderByStatement
     */
    public OrderByStatement<T> orderBy(String column, boolean ascending, Comparator comparator){
        return (OrderByStatement) (next=new OrderByStatement<T>(start,column,ascending,comparator));
    }

    /**
     *
     * Gets the name of the table
     *
     * @return the name of the table
     */
    public String getTable() {
        return table;
    }

    /**
     *
     * Gets name of the first column
     *
     * @return name of the first column
     */
    public String getColumn1() {
        return column1;
    }
    /**
     *
     * Gets name of the second column
     *
     * @return name of the second column
     */
    public String getColumn2() {
        return column2;
    }

    /**
     *
     * Gets the type of the join.
     *
     * @return the type of the join.
     */
    public Type getType() {
        return type;
    }

    /**
     *
     * processes the statement
     * @return CompletableFuture\<T\>
     */
    public CompletableFuture<T> process() {
        return start.process();
    }

}
