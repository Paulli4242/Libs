package xyz.dc_stats.database.local;

public abstract class Condition {

    private boolean and;
    private boolean not;
    protected int column;

    public Condition(boolean and,boolean not,int column){
        this.and = and;
        this.not = not;
        this.column = column;
    }

    abstract protected boolean is(byte[] c);
    public final boolean is(boolean b, byte[][] l){
        boolean c = is(l[column]);
        return b&&(c^not)||!and&&((c^not)||b);
    }
}
