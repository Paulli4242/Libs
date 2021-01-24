package xyz.dc_stats.utils.iteration;

import java.util.Iterator;

public class ArrayIterator<T> implements Iteration {
    T[] data;
    int i;

    public ArrayIterator(T[] data){
        this.data = data;
        i = 0;
    }

    @Override
    public boolean hasNext() {
        return data.length>i;
    }

    @Override
    public T next() {
        return data[i++];
    }

    @Override
    public Iterator iterator() {
        return this;
    }
}
