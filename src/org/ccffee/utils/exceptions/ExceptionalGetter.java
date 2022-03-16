package org.ccffee.utils.exceptions;

import java.lang.reflect.Constructor;

@FunctionalInterface
public interface ExceptionalGetter<T> {
    public T get()throws Exception;
    default T ignoreException(){
        try{
            return get();
        }catch (Exception e){
        }
        return null;
    }
    default T asRuntimeException(){
        try{
            return get();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
    default T asRuntimeException(Class<?extends RuntimeException> clazz){
        try {
            return get();
        }catch (Exception exception){
            RuntimeException e;
            try{
                Constructor<?extends RuntimeException> c = clazz.getDeclaredConstructor(Throwable.class);
                c.setAccessible(true);
                e = c.newInstance(exception);
            }catch (Exception ex){
                throw new RuntimeException(ex);
            }
            throw e;
        }
    }
    default T printStackTrace(){
        try{
            return get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
