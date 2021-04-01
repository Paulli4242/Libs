package xyz.dc_stats.database.local;

import xyz.dc_stats.database.ByteConvertable;

public class LessEqualCondition extends Condition{

    byte[] data;
    boolean negative;

    public LessEqualCondition(boolean and, boolean not, int column, ByteConvertable data) {
        super(and, not, column);
        this.data = data.toByteArray();
        if(this.data.length==0)this.data = new byte[]{0};
        negative = (this.data[0]&(byte)0x80)!=0;
    }

    @Override
    public boolean is(byte[] c) {
        if(c.length==0)c = new byte[]{0};
        int a,b,i=0;
        if(negative){
            if((c[0]&(byte)0x80)!=0){
                for(i = 0;i<data.length&&i<c.length;i++){
                    a=Byte.toUnsignedInt(data[i]);
                    b=Byte.toUnsignedInt(c[i]);
                    if(a<b)return false;
                    else if(a>b)return true;
                }
                if(c.length>i){
                    while (c.length<i){
                        if(c[i]!=(byte)0xff)return true ;
                        i++;
                    }
                }else if(data.length>i){
                    while (data.length<i){
                        if(data[i]!=(byte)0xff)return false;
                        i++;
                    }
                }
            }else return true;
        }else if((c[0]&(byte)0x80)!=0) return true;
        else{
            int diff = data.length-c.length;
            if(diff>0)for(i=0;i<diff;i++)if(data[i]!=0)return true;
            else if(diff<0){
                diff=-diff;
                for(i=0;i<0;i++)if(c[i]!=0)return false;
                i=0;
            }
            diff=-diff;
            while(i<data.length){
                if(data[i]<c[i+diff])return false;
                else if(data[i]>c[i+diff])return true;
                i++;
            }
        }
        return true;
    }
}
