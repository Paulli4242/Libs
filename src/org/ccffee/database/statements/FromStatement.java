package org.ccffee.database.statements;

import org.ccffee.database.comparison.Comparator;

import java.util.concurrent.CompletableFuture;

/**
 *
 * Class FromStatement represents a FROM statement.
 *
 * @param <T>
 */
public class FromStatement<T> implements JoinableStatement<T> {

    private String table;
    private AfterJoinableStatement<T> next;
    protected ProcessableStatement<T> start;

    FromStatement(ProcessableStatement<T> start, String table){
        this.table = table;
        this.start = start;
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
     * Gets the next statement
     *
     * @return the next statement
     */
    public AfterJoinableStatement<T> next(){
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
    public JoinStatement<T> join(JoinStatement.Type type,String table, String column1, String column2) {
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
     * Processes the statement
     *
     * @return CompletableFuture\<T\>
     */
    public CompletableFuture<T> process() {
        return start.process();
    }
}