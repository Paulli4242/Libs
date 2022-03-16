package org.ccffee.database.comparison;

/**
 *
 * Compares integer values in byte[] format. (Not only java integers)
 *
 */

public class IntegerComparator implements Comparator {
    /**
     *
     * Checks if current is less than other
     *
     * @param current current integer value
     * @param other other integer value
     * @return true if current is less than other, false otherwise
     */
    @Override
    public boolean less(byte[] current, byte[] other) {
        if(current.length==0)current = new byte[]{0};
        if(other.length==0)other = new byte[]{0};
        int a,b,i=0;
        if((other[0]&(byte)0x80)!=0){
            if((current[0]&(byte)0x80)!=0){
                for(i = 0;i<other.length&&i<current.length;i++){
                    a=Byte.toUnsignedInt(other[i]);
                    b=Byte.toUnsignedInt(current[i]);
                    if(a<b)return false;
                    else if(a>b)return true;
                }
                if(current.length>i){
                    while (current.length<i){
                        if(current[i]!=(byte)0xff)return true ;
                        i++;
                    }
                }else if(other.length>i){
                    while (other.length<i){
                        if(other[i]!=(byte)0xff)return false;
                        i++;
                    }
                }
            }else return true;
        }else if((current[0]&(byte)0x80)!=0) return true;
        else{
            int diff = other.length-current.length;
            if(diff>0)for(i=0;i<diff;i++)if(other[i]!=0)return true;
            else if(diff<0){
                diff=-diff;
                for(i=0;i<0;i++)if(current[i]!=0)return false;
                i=0;
            }
            diff=-diff;
            while(i<other.length){
                a=Byte.toUnsignedInt(other[i]);
                b=Byte.toUnsignedInt(current[i]);
                if(a<b)return false;
                else if(a>b)return true;
                i++;
            }
        }
        return false;
    }

    /**
     *
     * Checks if current is greater than other
     *
     * @param current current integer value
     * @param other other integer value
     * @return true if current is greater than other, false otherwise
     */
    @Override
    public boolean greater(byte[] current, byte[] other) {
        if(current.length==0)current = new byte[]{0};
        if(other.length==0)other = new byte[]{0};
        int a,b,i=0;
        if((other[0]&(byte)0x80)!=0){
            if((current[0]&(byte)0x80)!=0){
                for(i = 0;i<other.length&&i<current.length;i++){
                    a=Byte.toUnsignedInt(other[i]);
                    b=Byte.toUnsignedInt(current[i]);
                    if(a<b)return true;
                    else if(a>b)return false;
                }
                if(current.length>i){
                    while (current.length<i){
                        if(current[i]!=(byte)0xff)return false ;
                        i++;
                    }
                }else if(other.length>i){
                    while (other.length<i){
                        if(other[i]!=(byte)0xff)return true;
                        i++;
                    }
                }
            }else return true;
        }else if((current[0]&(byte)0x80)!=0) return false;
        else{
            int diff = other.length-current.length;
            if(diff>0)for(i=0;i<diff;i++)if(other[i]!=0)return false;
            else if(diff<0){
                diff=-diff;
                for(i=0;i<0;i++)if(current[i]!=0)return true;
                i=0;
            }
            diff=-diff;
            while(i<other.length){
                a=Byte.toUnsignedInt(other[i]);
                b=Byte.toUnsignedInt(current[i]);
                if(a<b)return true;
                else if(a>b)return false;
                i++;
            }
        }
        return false;
    }
}
