package xyz.dc_stats.jshs;

public class JshsValue implements JshsItem{
    Object obj;
    public JshsValue(Object obj){
        this.obj = obj;
    }
    public Object get(){
        return obj;
    }
}
