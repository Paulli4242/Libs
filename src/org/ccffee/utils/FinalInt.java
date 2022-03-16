package org.ccffee.utils;

public class FinalInt{

    int obj;

    public FinalInt(int obj){
        this.obj = obj;
    }

    public FinalInt(){
    }

    public int get() {
        return obj;
    }

    public FinalInt set(int obj) {
        this.obj = obj;
        return this;
    }
    public void inc(){
        obj++;
    }
    public void dec(){
        obj--;
    }
}
