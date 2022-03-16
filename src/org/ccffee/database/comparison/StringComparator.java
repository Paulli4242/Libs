package org.ccffee.database.comparison;

import org.ccffee.utils.io.ByteUtils;

/**
 *
 * Compares string values in byte[] format.
 *
 */
public class StringComparator implements Comparator {
    /**
     *
     * Checks if current is less than other
     *
     * @param current current string value
     * @param other other string value
     * @return true if current is less than other, false otherwise
     */
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
    /**
     *
     * Checks if current is greater than other
     *
     * @param current current string value
     * @param other other string value
     * @return true if current is greater than other, false otherwise
     */
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
