package xyz.dc_stats.database.comparison;

import xyz.dc_stats.utils.io.ByteUtils;

public class StringComparator implements Comparator {
    @Override
    public boolean less(byte[] current, byte[] other) {
        int i;
        for(i = 0;i+1<current.length&&i+1<other.length;i+=2){
            int c = ByteUtils.bytesToInt(new byte[]{0,0,current[i],current[i+1]});
            int o = ByteUtils.bytesToInt(new byte[]{0,0,other[i],other[i+1]});
            if(c<o)return true;
            else if(c>o) return false;
        }
        if(current.length>other.length)return false;
        else if(current.length<other.length)return true;
        else if(i<current.length)return current[i]<other[i];
        else return false;
    }

    @Override
    public boolean greater(byte[] current, byte[] other) {
        int i;
        for(i = 0;i+1<current.length&&i+1<other.length;i+=2){
            int c = ByteUtils.bytesToInt(new byte[]{0,0,current[i],current[i+1]});
            int o = ByteUtils.bytesToInt(new byte[]{0,0,other[i],other[i+1]});
            if(c>o)return true;
            else if(c<o) return false;
        }
        if(current.length<other.length)return false;
        else if(current.length>other.length)return true;
        else if(i<current.length)return current[i]>other[i];
        else return false;
    }
}
