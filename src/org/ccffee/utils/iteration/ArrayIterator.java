package org.ccffee.utils.iteration;

import java.util.Arrays;
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

    public static <T> Iteration<T> iteration(T... arr){
        return new ArrayIterator(arr);
    }
    public static <T> Iterator<T> iterator(T...arr){
        return new ArrayIterator(arr);
    }
}
