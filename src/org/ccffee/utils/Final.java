package org.ccffee.utils;

public class Final<T> {
    private T obj;

    public Final(T obj){
        this.obj = obj;
    }

    public Final(){

    }

    public T get() {
        return obj;
    }

    public Final<T> set(T obj) {
        this.obj = obj;
        return this;
    }
    public boolean isEmpty(){
        return obj==null;
    }
}
