package org.ccffee.utils.iteration;

import java.util.Iterator;

public class SimpleIterable<T> implements Iterable<T> {

    private Iterator<T> iterator;

    public SimpleIterable(Iterator<T> iterator){
        this.iterator = iterator;
    }

    @Override
    public Iterator<T> iterator() {
        return iterator;
    }
}
