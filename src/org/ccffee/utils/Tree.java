package org.ccffee.utils;

import org.ccffee.utils.exceptions.ExceptionUtils;
import org.ccffee.utils.iteration.ArrayUtils;

import java.lang.reflect.Array;
import java.nio.file.InvalidPathException;
import java.util.*;

public class Tree<K,V>{
    public class TreeEntry{
        private K key;
        private Object obj;

        protected TreeEntry(K key, Object obj) {
            this.key = key;
            this.obj = obj;
        }

        public boolean equalsKey(K key){
            return Objects.equals(this.key,key);
        }
        public boolean equalsValue(V value){
            return Objects.equals(this.obj,value);
        }
        public boolean isValue(){
            return !(obj instanceof Tree);
        }
        public boolean isTree(){
            return obj instanceof Tree;
        }
        public void clear(){
            key=null;
            if(isTree())((Tree)obj).clear();
            obj=null;
        }

        public K getKey() {
            return key;
        }
        public V getValue(){
            return ExceptionUtils.getIE(()->(V)obj);
        }
        public Tree<K,V> getTree(){
            return ExceptionUtils.getIE(()->(Tree) obj);
        }
        public V setValue(V value){
            V v=ExceptionUtils.getIE(()->(V)obj);
            obj = value;
            return v;
        }
        public void setTree(Tree<K, V> tree){
            obj = tree;
            tree.setParent(Tree.this);
        }
        public Tree<K,V> getParent(){
            return Tree.this;
        }
        public V remove(){
            return removeLevelEntry(key);
        }

        @Override
        public String toString() {
            return "TreeEntry{" +
                    "key=" + key +
                    ", obj=" + obj +
                    '}';
        }
    }

    protected Tree parent;
    protected int lvlSize;
    protected int totalSize;
    protected TreeEntry[] arr;

    public int getLevelSize() {
        return lvlSize;
    }
    public int getTotalSize() {
        return totalSize;
    }
    public Tree getParent() {
        return parent;
    }

    protected void setParent(Tree parent) {
        this.parent = parent;
    }

    protected int getEntryIndex(K k){
        for(int i = 0; i<arr.length; i++)if(arr[i].equalsKey(k))return i;
        return -1;
    }

    public Tree(){
        this(0);
    }
    public Tree(int capacity){
        this(null,capacity);
    }
    protected Tree(Tree parent){
        this(parent,0);
    }
    protected Tree(Tree parent, int capacity){
        this.parent = parent;
        lvlSize=0;
        totalSize=0;
        arr = (TreeEntry[]) Array.newInstance(TreeEntry.class,capacity);
    }
    public TreeEntry getLevelEntry(K k){
        return ExceptionUtils.getIE(()->arr[getEntryIndex(k)]);
    }

    public TreeEntry getEntry(K...path)throws InvalidPathException{
        return getEntry(path,0);
    }
    protected TreeEntry getEntry(K[] path,int pos)throws InvalidPathException{
        TreeEntry e = getLevelEntry(path[pos]);
        if(e!=null){
            if(pos<path.length-1){
                if(e.isTree())return e.getTree().getEntry(path,pos+1);
                else throw new InvalidPathException(ArrayUtils.toString(path),"at "+path[pos]+" is no tree");
            }else return e;
        }else throw new InvalidPathException(ArrayUtils.toString(path),"at "+path[pos]+" is no tree");
    }
    public TreeEntry getFirstEntryByValue(V value)throws InvalidPathException{
        for(TreeEntry e : arr) {
            if (e.isTree()) {
                TreeEntry r = e.getTree().getFirstEntryByValue(value);
                if (r != null) return r;
            } else if (e.equalsValue(value)) return e;
        }return null;
    }
    public V removeLevelEntry(K k){
        int i = getEntryIndex(k);
        V v = null;
        if(i>-1){
            v = arr[i].getValue();
            if(arr[i].isValue())decreaseTotalSize();
            arr[i].clear();
            arr=ArrayUtils.removeAndShrink(arr,i);
            lvlSize--;
        }
        return v;
    }
    protected void increaseTotalSize(){
        totalSize++;
        if(parent!=null)parent.increaseTotalSize();
    }
    protected void decreaseTotalSize(){
        totalSize--;
        if(parent!=null)parent.increaseTotalSize();
    }
    public V removeEntry(K...k){
        return ExceptionUtils.getIE(()->getEntry(k).remove());
    }
    public V put(V v, K...path){
        return put(v,path,0);
    }
    protected V put(V v,K[] path,int pos)throws InvalidPathException{
        TreeEntry e = getLevelEntry(path[pos]);
        if(e!=null){
            if(pos<path.length-1){
                if(e.isTree())return e.getTree().put(v,path,pos+1);
                else throw new InvalidPathException(ArrayUtils.toString(path),"at "+path[pos]+" is no tree");
            }else{
                V out = e.getValue();
                e.setValue(v);
                return out;
            }
        }else createNewEntry(path,v,pos);
        return null;
    }
    protected void createNewEntry(K[] path,Object o,int pos){
        if(pos<path.length-1){
            Tree t = new Tree(this,1);
            if(lvlSize<arr.length)arr[lvlSize]=new TreeEntry(path[pos],t);
            else arr = ArrayUtils.addAndExpand(arr,new TreeEntry(path[pos],t));
            t.createNewEntry(path,o,pos+1);
        }else{
            if(lvlSize<arr.length)arr[lvlSize]=new TreeEntry(path[pos],o);
            else arr = ArrayUtils.addAndExpand(arr,new TreeEntry(path[pos],o));
            increaseTotalSize();
        }
        lvlSize++;
    }

    public int size() {
        return lvlSize;
    }

    public boolean isEmpty() {
        return lvlSize==0;
    }

    public boolean containsKey(Object o) {
        return ExceptionUtils.getIE(()->getEntry((K)o)!=null);
    }

    public boolean containsValue(Object o) {
        return ExceptionUtils.getIE(()->getFirstEntryByValue((V) o))!=null;
    }

    public V get(K o) {
        return ExceptionUtils.getIE(()->getEntry(o).getValue());
    }
    public V get(K...o) {
        return ExceptionUtils.getIE(()->getEntry(o).getValue());
    }
    public V put(K k, V v) {
        return put(v,k);
    }

    public V remove(Object o) {
        return ExceptionUtils.getIE(()->removeEntry((K)o));
    }
    public void putAll(Map<? extends K, ? extends V> map) {

    }

    public void clear() {
        arr = (TreeEntry[]) Array.newInstance(TreeEntry.class,0);
    }

    public Collection<V> values() {
        return null;
    }

    public Set<TreeEntry> entrySet() {
        return Set.of(Arrays.copyOf(arr,lvlSize));
    }

    @Override
    public String toString() {
        return "Tree{" +
                ", lvlSize=" + lvlSize +
                ", totalSize=" + totalSize +
                ", arr=" + ArrayUtils.toString(arr) +
                '}';
    }
}
