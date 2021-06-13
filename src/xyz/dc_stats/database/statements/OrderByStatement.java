package xyz.dc_stats.database.statements;

import xyz.dc_stats.database.comparison.Comparator;

import java.util.concurrent.CompletableFuture;

public class OrderByStatement<T> implements AfterWhereEndStatement<T>, AfterJoinableStatement<T> {

    private ProcessableStatement<T> start;
    private String column;
    private boolean ascending;
    private Comparator comparator;

    public OrderByStatement(ProcessableStatement<T> start, String column, boolean ascending, Comparator comparator) {
        this.start = start;
        this.column = column;
        this.ascending = ascending;
        this.comparator = comparator;
    }



    public Comparator getComparator() {
        return comparator;
    }

    public String getColumn() {
        return column;
    }

    public boolean isAscending() {
        return ascending;
    }

    public CompletableFuture<T> process(){
        return start.process();
    }

}
