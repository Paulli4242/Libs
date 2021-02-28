package xyz.dc_stats.utils.tag;

import xyz.dc_stats.utils.iteration.ArrayIterator;
import xyz.dc_stats.utils.iteration.ArrayUtils;

import java.util.Iterator;

public class TagTree implements Iterable<Object> {

   private String name;
   private Object tree[];
   private TagTree parent;

    public TagTree(){
        tree = new Object[0];
        name = "";
    }
    public TagTree(String name,TagTree parent){
        tree = new Object[0];
        this.name = name;
        this.parent = parent;
    }
    public void add(Object obj){
        tree = ArrayUtils.addAndExpand(tree,obj);
    }
    public void set(int index, Object obj){
        tree[index]=obj;
    }
    public Object get(int i) {
        if(tree.length>i)return tree[i];
        else return null;
    }
    public boolean isTree(int i){
        if(tree.length>i)return tree[i]instanceof TagTree;
        return false;
    }
    public <T> T get(int i, Class<T> clazz){
        if(clazz.isInstance(get(i)))return (T)get(i);
        return null;
    }
    public TagTree getTree(int i) {
        if(isTree(i))return (TagTree) get(i);
        else return null;
    }
    public TagTree getParent(){
        return parent;
    }
    public String getName() {
        return name;
    }
    public Object[] getData(){return tree;}
    @Override
    public String toString() {
        String out = "<"+name+">";
        for(Object o : tree)out+=o;
        return out+"</"+name+">";
    }
    public String format(){
        return format("");
    }
    public String format(String tabs){
        String out = tabs+"<"+name.toUpperCase()+">\n";
        for(Object o : tree){
            if(o instanceof TagTree)
                out+=((TagTree)o).format(tabs+"\t")+"\n";
            else out+=tabs+"\t"+o+"\n";
        }
        return out+tabs+"</"+name.toUpperCase()+">";
    }
    public static TagTree parse(String s) throws IllegalSyntaxException{
        TagTreeBuilder treeb = new TagTreeBuilder();
        boolean inTag = false;
        String current = "";
        boolean ctrl;
        boolean setCtrl = false;
        int ln = 1;
        int col = 0;
        try{
            for(char c : s.toCharArray()){
                col++;
                if(c=='\n'){
                    ln++;
                    col=0;
                }
                ctrl = setCtrl;
                setCtrl = false;
                if(c == '<'&&!ctrl){
                    if(!current.trim().isEmpty()){
                        treeb.add(current.trim());
                    }
                    current="";
                    inTag=true;
                }else if(c=='>'&&inTag){
                    inTag=false;
                    if(current.startsWith("/"))treeb.closeTag(current.substring(1));
                    else treeb.startTag(current);
                    current="";
                }else if(ctrl&&c=='n')current+='\n';
                else if(c=='\\'&&!ctrl)setCtrl=true;
                else current+=c;
            }
        }catch (IllegalSyntaxException e){
            throw new IllegalSyntaxException(e.getMessage(),e.getType(),ln,col);
        }
        return treeb.build();
    }
    @Override
    public Iterator<Object> iterator() {
        return new ArrayIterator<Object>(tree);
    }
}