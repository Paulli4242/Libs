package xyz.dc_stats.utils.iteration;

public class Final2DArray<T> extends FinalArray<FinalArray<T>> {

    public Final2DArray(T[][] array){
        this(build(array));
    }

    public Final2DArray(FinalArray<T>[] array) {
        super(array);
    }
    private static <T> FinalArray<T>[] build(T[][] array){
        FinalArray<T>[] a = new FinalArray[array.length];
        for(int i = 0;i<a.length;i++)a[i]=new FinalArray<>(array[i]);
        return a;
    }

    public T get(int i1, int i2){
        return get(i1).get(i2);
    }

}
