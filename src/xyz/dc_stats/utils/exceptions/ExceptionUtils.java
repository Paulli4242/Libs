package xyz.dc_stats.utils.exceptions;

public final class ExceptionUtils {
    private ExceptionUtils(){
    }

    public static <T> T getIE(ExceptionalGetter<T> getter){
        return getter.ignoreException();
    }
    public static void runIE(ExceptionalRunnable run){
        run.ignoreException();
    }

}
