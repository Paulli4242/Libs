package xyz.dc_stats.database.local;

import xyz.dc_stats.database.ByteConvertable;

import java.math.BigInteger;

public class LessCondition extends Condition{

    byte[] data;
    boolean negative;

    public LessCondition(boolean and, boolean not, int column, ByteConvertable data) {
        super(and, not, column);
        this.data = data.toByteArray();
        negative = (this.data[0]&(byte)0x80)!=0;
    }

    @Override
    protected boolean is(byte[] c) {
        int a,b,i;
        if(negative){
            if((c[0]&(byte)0x80)!=0){
                for(i = 0;i<data.length&&i<c.length;i++){
                    a=Byte.toUnsignedInt(data[i]);
                    b=Byte.toUnsignedInt(c[i]);
                    if(a<b)return true;
                    else if(a>b)return false;
                }
                if(c.length>i){
                    while (c.length<i){
                        if(c[i]!=(byte)0xff)return false ;
                        i++;
                    }
                }else if(data.length>i){
                    while (data.length<i){
                        if(data[i]!=(byte)0xff)return true;
                        i++;
                    }
                }
            }else return true;
        }else if((c[0]&(byte)0x80)!=0) return false;
        else{

        }
        return false;
    }
}
