package xyz.dc_stats.utils.exceptions;

@FunctionalInterface
public interface ExceptionalRunnable {
    public void run()throws Exception;
    default void ignoreException(){
        try{
            run();
        }catch (Exception e){
        }
    }
}
