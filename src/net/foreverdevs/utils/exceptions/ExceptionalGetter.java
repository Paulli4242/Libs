package net.foreverdevs.utils.exceptions;
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
}
