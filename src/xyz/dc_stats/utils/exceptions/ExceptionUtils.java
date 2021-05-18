package xyz.dc_stats.utils.exceptions;

public final class ExceptionUtils {
    private ExceptionUtils(){
    }

    public static <T> T getIE(ExceptionalGetter<T> getter){
        return getter.ignoreException();
    }
    public static <T> T getIE(ExceptionalGetter<T> getter, T def){
        T  t = getter.ignoreException();
        return t==null?def:t;
    }
    public static void runIE(ExceptionalRunnable run){
        run.ignoreException();
    }
    public static <T> T getAsRE(ExceptionalGetter<T> getter){
        return getter.asRuntimeException();
    }
    public static void runAsRE(ExceptionalRunnable run){
        run.asRuntimeException();
    }
    public static <T> T getAsRE(ExceptionalGetter<T> getter,Class<?extends RuntimeException> ex){
        return getter.asRuntimeException(ex);
    }
    public static void runAsRE(ExceptionalRunnable run,Class<?extends RuntimeException> ex){
        run.asRuntimeException(ex);
    }
}
