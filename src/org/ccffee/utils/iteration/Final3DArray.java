package org.ccffee.utils.iteration;

public class Final3DArray<T> extends FinalArray<Final2DArray<T>>{

    public Final3DArray(T[][][] array){
        this(build(array));
    }

    public Final3DArray(Final2DArray<T>[] array) {
        super(array);
    }

    private static <T> Final2DArray<T>[] build(T[][][] array){
        Final2DArray<T>[] a = new Final2DArray[array.length];
        for(int i = 0;i<a.length;i++)a[i]=new Final2DArray<>(array[i]);
        return a;
    }
    public T get(int i1, int i2, int i3){
        return get(i1).get(i2,i3);
    }

}
