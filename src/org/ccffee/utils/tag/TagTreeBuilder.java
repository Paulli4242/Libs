package org.ccffee.utils.tag;

public class TagTreeBuilder {
    TagTree tree;
    TagTree parent;
    public TagTreeBuilder(){
        tree = new TagTree();
        parent = tree;
    }
    public TagTreeBuilder add(Object obj){
        tree.add(obj);
        return this;
    }
    public TagTreeBuilder startTag(String name){
        TagTree t = new TagTree(name, tree);
        tree.add(t);
        tree = t;
        return this;
    }
    public TagTreeBuilder closeTag(String name) throws IllegalSyntaxException {
        if(tree.getName().equalsIgnoreCase(name))tree = tree.getParent();
        else throw new IllegalSyntaxException("Closed Tag wasn't the current.", IllegalSyntaxException.Type.WRONG_CLOSETAG);
        return this;
    }
    public TagTree build(){
        return parent;
    }
}
