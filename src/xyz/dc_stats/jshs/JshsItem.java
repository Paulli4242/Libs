package xyz.dc_stats.jshs;

import java.lang.reflect.InvocationTargetException;

public interface JshsItem {
    Object get() throws IllegalStateException, InvocationTargetException;
}
