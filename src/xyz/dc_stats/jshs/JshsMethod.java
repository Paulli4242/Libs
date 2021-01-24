package net.foreverdevs.jshs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.foreverdevs.utils.iteration.ArrayUtils;

public class JshsMethod implements JshsItem {
  Object obj;
  JshsItem vars[];
  Method method;
  JshsMethod futureMethod;
  public JshsMethod(Method method){
    this.method=method;
    vars = new JshsItem[0];
  }
  public void addParameterMethod(JshsMethod method){
    vars = ArrayUtils.addAndExpand(vars,new JshsValue(obj));
  }
  public void addParameter(Object obj){
    vars = ArrayUtils.addAndExpand(vars,new JshsValue(obj));
  }
  public void setFutureMethod(JshsMethod method){
    futureMethod = method;
  }

  public Object get(){
    try {
      return execute();
    } catch (InvocationTargetException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
  public Object execute() throws InvocationTargetException, IllegalAccessException {
    return execute(obj);
  }
  public Object execute(Object obj) throws InvocationTargetException, IllegalAccessException {
    Object[] objs = new Object[vars.length];
    for(int i = 0;i<vars.length;i++)objs[i]=vars[i].get();
    return futureMethod ==null?method.invoke(obj,objs): futureMethod.execute(method.invoke(obj,objs));
  }
}