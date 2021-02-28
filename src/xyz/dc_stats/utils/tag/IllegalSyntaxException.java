package xyz.dc_stats.utils.tag;

public class IllegalSyntaxException extends Exception {
    private int ln;
    private int col;
    private Type type;
    public IllegalSyntaxException(){
        type = Type.UNKNOWN;
        ln=-1;
        col=-1;
    }
    public IllegalSyntaxException(String s, Type type){
        super(s);
        this.type = type;
        ln=-1;
        col=-1;
    }
    public IllegalSyntaxException(String s, Type type, int ln, int col){
        super(s);
        this.type = type;
        this.ln = ln;
        this.col = col;
    }

    public int getLine() {
        return ln;
    }

    public int getColumn() {
        return col;
    }

    public Type getType() {
        return type;
    }

    public enum Type{
        UNKNOWN,
        WRONG_CLOSETAG;
    }
}
