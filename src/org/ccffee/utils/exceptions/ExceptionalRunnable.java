package org.ccffee.utils.exceptions;

import java.lang.reflect.Constructor;

@FunctionalInterface
public interface ExceptionalRunnable {
    public void run()throws Exception;
    default void ignoreException(){
        try{
            run();
        }catch (Exception e){
        }
    }
    default void asRuntimeException(){
        try{
            run();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
    default void asRuntimeException(Class<?extends RuntimeException> clazz){
        try {
            run();
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
    default void printStackTrace(){
        try{
            run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
