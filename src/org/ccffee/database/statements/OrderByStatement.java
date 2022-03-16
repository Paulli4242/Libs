package org.ccffee.database.statements;

import org.ccffee.database.comparison.Comparator;

import java.util.concurrent.CompletableFuture;


/**
 *
 * Class OrderByStatement represents a ORDER BY statement
 *
 * @param <T>
 */
public class OrderByStatement<T> implements AfterWhereEndStatement<T>, AfterJoinableStatement<T> {

    private ProcessableStatement<T> start;
    private String column;
    private boolean ascending;
    private Comparator comparator;

    OrderByStatement(ProcessableStatement<T> start, String column, boolean ascending, Comparator comparator) {
        this.start = start;
        this.column = column;
        this.ascending = ascending;
        this.comparator = comparator;
    }


    /**
     *
     * Gets the Comparator
     *
     * @return the Comparator
     */
    public Comparator getComparator() {
        return comparator;
    }

    /**
     *
     * Gets the name of the column
     *
     * @return the name of the column
     */
    public String getColumn() {
        return column;
    }

    /**
     *
     * Checks if is ascending
     *
     * @return true if is ascending, false otherwise
     */
    public boolean isAscending() {
        return ascending;
    }

    /**
     *
     * process the statement
     *
     * @return CompletableFuture\<T\>
     */
    public CompletableFuture<T> process(){
        return start.process();
    }

}
