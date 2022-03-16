package org.ccffee.extension;

import org.ccffee.logger.Logger;
import org.ccffee.utils.exceptions.ExceptionUtils;
import org.ccffee.utils.iteration.ArrayUtils;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Objects;

public class ExtendedMethod {
    Object obj;
    Method method;

    public ExtendedMethod(Object obj, Method method) {
        this.obj = obj;
        this.method = method;
        method.setAccessible(true);
    }

    public void invoke(ExtensionContext context, Object...args){
        try {
            method.invoke(obj, ArrayUtils.addAndExpand(args,context));
        } catch (Exception e) {
            PrintStream err = null;
            if(Logger.exist()) ExceptionUtils.getIE(Logger.getLogger()::getErrorStream,System.err);
            else err = System.err;
            e.printStackTrace(err);
        }
    }


    @Override
    public boolean equals(Object o) {
        if(!(o instanceof ExtendedMethod))return false;
        ExtendedMethod m = (ExtendedMethod) o;
        return Objects.equals(obj,m.obj)&&Objects.equals(method,m.method);
    }
}
