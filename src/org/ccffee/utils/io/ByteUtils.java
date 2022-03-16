package org.ccffee.utils.io;

import org.ccffee.utils.iteration.ArrayUtils;

import java.util.Arrays;

public final class ByteUtils {
	private ByteUtils(){

	}
	public static byte[] numberToBytes(Number ni) {
		if(ni instanceof Float)ni = Float.floatToRawIntBits((float) ni);
		else if(ni instanceof Double)ni = Double.doubleToRawLongBits((double) ni);
		byte[] bytes = new byte[0];
		if(ni instanceof Integer) {
			int n = (int) ni;
			bytes = new byte[Integer.BYTES];
			for(int i=0;i<bytes.length;i++) bytes[i]=(byte) (n>>(8*(bytes.length-1-i)));
		}else if(ni instanceof Long) {
			long n = (long) ni;
			bytes = new byte[Long.BYTES];
			for(int i=0;i<bytes.length;i++) bytes[i]=(byte) (n>>(8*(bytes.length-1-i)));
		}else if(ni instanceof Short) {
			short n = (short) ni;
			bytes = new byte[Short.BYTES];
			for(int i=0;i<bytes.length;i++) bytes[i]=(byte) (n>>(8*(bytes.length-1-i)));
		}else if(ni instanceof Byte) {
			bytes = new byte[] {(byte) ni};
		}
		return bytes;
	}
	public static byte[] charToBytes(char c) {
		byte[] i = numberToBytes((int)c);
		return Arrays.copyOfRange(i, 2, 4);
	}
	public static byte[] charArrayToBytes(char[] ch){
		byte[] bytes = new byte[ch.length*2];
		for(int i = 0;i<ch.length;i++) {
			byte[] b = charToBytes(ch[i]);
			bytes[i*2]=b[0];
			bytes[i*2+1]=b[1];
		}
		return bytes;
	}
	public static byte[] stringToBytes(String s) {
		if(s==null)return new byte[0];
		return charArrayToBytes(s.toCharArray());
	}
	public static byte[] boolToBytes(boolean b){
		return new byte[]{(byte) (b?1:0)};
	}

	public static int bytesToInt(byte[] bytes) {
		int out=0;
		for(int i=bytes.length-1; i>=0;i--) out += (((int)bytes[i])<<24)>>>(8*i);
		return out;
	}
	public static long bytesToLong(byte[] bytes) {
		long out=0;
		for(int i=bytes.length-1; i>=0;i--) out += (((long)bytes[i])<<56)>>>(8*i);
		return out;
	}
	public static short bytesToShort(byte[] bytes) {
		short out=0;
		for(int i=bytes.length-1; i>=0;i--) out += (((short)bytes[i])<<8)>>>(8*i);
		return out;
	}
	public static double bytesToDouble(byte[] bytes) {
		return Double.longBitsToDouble(bytesToLong(bytes));
	}
	public static float bytesToFloat(byte[] bytes) {
		return Float.intBitsToFloat(bytesToInt(bytes));
	}
	public static char bytesToChar(byte[] bytes) {
		return (char) bytesToInt(new byte[] {0,0,bytes[0],bytes[1]});
	}
	public static char[] bytesToCharArray(byte[] bytes){
		if(bytes==null)return null;
		char[]ch = new char[bytes.length/2];
		byte[] b = new byte[2];
		for(int i = 0;i<ch.length;i++) {
			b[0]=bytes[i*2];
			b[1]=bytes[i*2+1];
			ch[i] = ByteUtils.bytesToChar(b);
		}
		return ch;
	}
	public static String bytesToString(byte[] bytes) {
		return new String(bytesToCharArray(bytes));
	}
	public static boolean bytesToBoolean(byte[] bytes){
		return bytes[0]==1;
	}
	public static boolean byteToBoolean(byte b){
		return b==1;
	}

	public static byte[] pack(byte[]...bts){
		int i = 0;
		byte[] out;
		for(byte[] b : bts)i += b.length/255+1+b.length;
		out = new byte[i];
		i = 0;
		for(byte[] b : bts){
			for(int z = 0;z<b.length;z++){
				if(z%255==0){
					if(b.length-z>=255)out[i]=(byte)255;
					else out[i] = (byte)(b.length-z);
					i++;
				}
				out[i]=b[z];
				i++;
			}
			if(b.length%255==0)out[i++]=0;
		}
		return out;
	}
	public static byte[][] unpack(byte[] bts){
		int z=0;
		int j=0;
		boolean next = false;
		byte[][] out = bts.length>0? new byte[1][0]:new byte[0][];
		for(byte b:bts){
			if(z<out[j].length){
				out[j][z]=b;
				z++;
			}else{
				if(next){
					out = ArrayUtils.addAndExpand(out,new byte[Byte.toUnsignedInt(b)]);
					j++;
					z=0;
				}else out[j]= ArrayUtils.expand(out[j],Byte.toUnsignedInt(b));
				next = Byte.toUnsignedInt(b)<255;
			}
		}
		return out;
	}
}
