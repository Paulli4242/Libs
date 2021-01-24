package net.foreverdevs.jshs;

import java.lang.reflect.InvocationTargetException;

public interface JshsItem {
    Object get() throws IllegalStateException, InvocationTargetException;
}
