package xyz.dc_stats.utils.iteration;

import java.util.Arrays;
import java.util.Iterator;

public class FinalArray<T> implements Iterable<T>{
    private final T[] array;

    public FinalArray(T[] array){
        this.array = array;
    }

    public T get(int index){
        return array[index];
    }

    public int length(){
        return array.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<>(array);
    }

    @Override
    public String toString() {
        return "FinalArray{" +
                "array=" + ArrayUtils.toString(array) +
                '}';
    }
}
