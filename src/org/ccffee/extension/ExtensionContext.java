package org.ccffee.extension;

import java.util.Objects;

public class ExtensionContext<O,R> {
    private R returnValue;
    private boolean canceled;
    private boolean empty;
    private O obj;
    private Class<O> clazz;
    private Class<R> returnType;
    private String method;

    public ExtensionContext(O obj, Class<O> clazz, Class<R> returnType, String method) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(returnType);
        Objects.requireNonNull(method);
        this.obj = obj;
        this.clazz = clazz;
        this.returnType = returnType;
        this.method = method;
        empty=true;
    }

    public void cancel(){
        cancel(true);
    }
    public void cancel(R returnValue)throws InvalidReturnTypeException{
        cancel(true,returnValue);
    }
    public void cancel(boolean cancel){
        canceled = canceled||cancel;
    }
    public void cancel(boolean cancel,R returnValue)throws InvalidReturnTypeException{
        cancel(cancel);
        cancel0(returnValue);
    }
    private void cancel0(R returnValue)throws InvalidReturnTypeException{
        if(returnValue!=null&&!returnType.isInstance(returnValue))throw new InvalidReturnTypeException(returnType);
        if(empty){
            this.returnValue = returnValue;
            empty=false;
        }
    }

    public boolean isCanceled() {
        return canceled;
    }

    public R getReturnValue() {
        return returnValue;
    }
    public boolean isEmpty(){
        return empty;
    }

    public Class<O> getClazz() {
        return clazz;
    }

    public String getMethod() {
        return method;
    }

    public O getObject() {
        return obj;
    }
}
