package xyz.dc_stats.utils.iteration;

import java.util.Iterator;
import java.util.function.Function;

public class ConversionIteration<T,U> implements Iteration<U> {
    private Iterator<T> iterator;
    private Function<T,U> f;

    public ConversionIteration(Iterator<T> iterator, Function<T,U> f){
        this.iterator = iterator;
        this.f = f;
    }
    public ConversionIteration(Iterable<T> iterable, Function<T,U> f){
        this.iterator = iterable.iterator();
        this.f = f;
    }

    @Override
    public Iterator<U> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public U next() {
        return f.apply(iterator.next());
    }
}
