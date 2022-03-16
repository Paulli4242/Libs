package org.ccffee.extension;


import org.ccffee.utils.Tree;
import org.ccffee.utils.exceptions.ExceptionUtils;
import org.ccffee.utils.iteration.ArrayUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Extensions {
    protected Tree<String,List<ExtendedMethod>> tree = new Tree<>();
    public void add(Object obj) throws NoSuchMethodException {
        Class<?> cls = obj.getClass();
        Class<?>[] defCls = ExceptionUtils.getIE(()->cls.getDeclaredAnnotation(Extension.class).value(),new Class<?>[0]);
        for(Method m : cls.getDeclaredMethods()){
            Extends e = m.getAnnotation(Extends.class);
            if(e!=null){
                int pct = m.getParameterCount();
                if(pct>0&&m.getParameterTypes()[--pct].equals(ExtensionContext.class)){
                    Class<?>[] tarCls = null;
                    if(!e.clazz()[0].equals(Void.class))tarCls=e.clazz();
                    if(tarCls==null)tarCls=defCls;
                    int i = 0;
                    Class<?>[] param = Arrays.copyOf(m.getParameterTypes(),pct);
                    if(e.value().equals("<init>"))while(i<tarCls.length){
                        Class<?>c=tarCls[i];
                        if(ExceptionUtils.getIE(()->c.getDeclaredConstructor(param))==null)
                            tarCls= ArrayUtils.removeAndShrink(tarCls,i);
                        else i++;
                    } else while(i<tarCls.length){
                        Class<?>c=tarCls[i];
                        if(ExceptionUtils.getIE(()->c.getDeclaredMethod(e.value(),param))==null)
                            tarCls=ArrayUtils.removeAndShrink(tarCls,i);
                        else i++;
                    }
                    add(tarCls,e.value(),new ExtendedMethod(obj,m));
                }else throw new NoSuchMethodException("Missing last parameter: "+ExtensionContext.class);
            }
        }
    }
    public void add(Class<?>[] cls,String methodName, ExtendedMethod method){
        for(Class<?> c : cls){
            List<ExtendedMethod> l = tree.get(c.getName(),methodName);
            if(l!=null)l.add(method);
            else tree.put(new ArrayList<>(Set.of(method)),c.getName(),methodName);
        }
    }
    public void remove(Object obj) throws NoSuchMethodException {
        Class<?> cls = obj.getClass();
        Class<?>[] defCls = ExceptionUtils.getIE(()->cls.getDeclaredAnnotation(Extension.class).value(),new Class<?>[0]);
        for(Method m : cls.getDeclaredMethods()){
            Extends e = m.getAnnotation(Extends.class);
            if(e!=null){
                int pct = m.getParameterCount();
                if(pct>0&&m.getParameterTypes()[--pct].equals(ExtensionContext.class)){
                    Class<?>[] tarCls = null;
                    if(!e.clazz()[0].equals(Void.class))tarCls=e.clazz();
                    if(tarCls==null)tarCls=defCls;
                    int i = 0;
                    Class<?>[] param = Arrays.copyOf(m.getParameterTypes(),pct);
                    if(e.value().equals("<init>"))while(i<tarCls.length){
                        Class<?>c=tarCls[i];
                        if(ExceptionUtils.getIE(()->c.getDeclaredConstructor(param))==null)
                            tarCls=ArrayUtils.removeAndShrink(tarCls,i);
                        else i++;
                    } else while(i<tarCls.length){
                        Class<?>c=tarCls[i];
                        if(ExceptionUtils.getIE(()->c.getDeclaredMethod(e.value(),param))==null)
                            tarCls=ArrayUtils.removeAndShrink(tarCls,i);
                        else i++;
                    }
                    remove(tarCls,e.value(),new ExtendedMethod(obj,m));
                }else throw new NoSuchMethodException("Missing last parameter: "+ExtensionContext.class);
            }
        }
    }
    public void remove(Class<?>[] cls,String methodName, ExtendedMethod method){
        for(Class<?> c : cls){
            List<ExtendedMethod> l = tree.get(c.getName(),methodName);
            if(l!=null)l.remove(method);
        }
    }
    public void handle(ExtensionContext context, Object...args){
        List<ExtendedMethod> l = tree.get(context.getClazz().getName(),context.getMethod());
        if(l!=null&&!l.isEmpty())
            for(ExtendedMethod m : l){
                m.invoke(context,args);
            }
    }

}
