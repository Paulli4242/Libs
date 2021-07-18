package xyz.dc_stats.utils.iteration;

public class Final4DArray<T> extends FinalArray<Final3DArray<T>> {

    public Final4DArray(T[][][][] array) {
        super(build(array));
    }

    public Final4DArray(Final3DArray<T>[] array) {
        super(array);
    }
    private static <T> Final3DArray<T>[] build(T[][][][] array){
        Final3DArray<T>[] a = new Final3DArray[array.length];
        for(int i = 0;i<a.length;i++)a[i]=new Final3DArray<>(array[i]);
        return a;
    }

    public T get(int i1, int i2, int i3, int i4){
        return get(i1).get(i2,i3,i4);
    }
}
