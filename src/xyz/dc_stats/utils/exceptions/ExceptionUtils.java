package net.foreverdevs.utils.exceptions;


import net.foreverdevs.utils.functional.Getter;

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
