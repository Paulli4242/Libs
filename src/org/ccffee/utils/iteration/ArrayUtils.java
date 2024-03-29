package org.ccffee.utils.iteration;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public final class ArrayUtils {
	private ArrayUtils(){

	}
	//Default
	public static String toString(Object args) {
		if(args==null)return "null";
		if(!args.getClass().isArray())return args.toString();
		String out = "[";
		int i;
		for(i = 0; i<Array.getLength(args)-1;i++) out+=toString(Array.get(args, i))+",";
		if(Array.getLength(args)>0)out+=toString(Array.get(args, i));
		return out+"]";
	}
	public static <T> T[] expand(T[] arr, int i){
		return Arrays.copyOf(arr, arr.length+i);
	}
	public static <T> T[] addAndExpand(T[] arr, T obj) {
		arr = expand(arr, 1);
		arr[arr.length-1] = obj;
		return arr;
	}
	public static <T> T[] addAndExpand(T[] arr, T obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, 1);
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i-1];
		arr[index]=obj;
		return arr;
	}
	public static <T> T[] addArrayAndExpand(T[] arr, T[] obj) {
		int len = arr.length;
		arr = expand(arr, obj.length);
		for(int i = 0;i< obj.length;i++){
			arr[len+i]=obj[i];
		}
		return arr;
	}
	public static <T> T[] addArrayAndExpand(T[] arr, T[] obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, obj.length);
		index+=obj.length-1;
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i- obj.length];
		index-= obj.length-1;
		for(int i = 0;i<obj.length;i++)arr[index+i]=obj[i];
		return arr;
	}
	public static <T> T[] addCollectionAndExpand(T[] arr, Collection<?extends T> obj) {
		int i = arr.length;
		arr = expand(arr,obj.size());
		for(T o : obj)arr[i++]=o;
		return arr;
	}
	public static <T> T[] addCollectionAndExpand(T[] arr, Collection<?extends T> obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		int size = obj.size();
		arr = expand(arr, size);
		index+= size-1;
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i- size];
		index-= size-1;
		for(T o:obj)arr[index++]=o;
		return arr;
	}

	public static <T> T[] shrink(T[] arr, int i) {
		return Arrays.copyOf(arr, arr.length-i);
	}
	public static <T> T[] removeAndShrink(T[] arr, int i) {
		if(i>=arr.length||i<0)throw new IllegalArgumentException(i+" isn't in range 0 - "+arr.length);
		for(i = i+1;i<arr.length;i++)arr[i-1]=arr[i];
		return shrink(arr, 1);
	}
	public static <T> T[] shuffle(T[] arr) {
		Random r = new Random();
		int ri;
		T obj;
		for(int i = 0;i<arr.length;i++) {
			ri=r.nextInt(arr.length);
			obj = arr[i];
			arr[i]=arr[ri];
			arr[ri]=obj;
		}
		return arr;
	}
	public static <T> T[] shuffleInTo(T[] arr,T obj) {
		return addAndExpand(arr,obj, new Random().nextInt(arr.length+1));
	}
	public static <T> T[] shuffleInTo(T[] arr,T obj,int from, int to) {
		if(to>arr.length+1||to<=from)throw new IllegalArgumentException("Invalid range");
		return addAndExpand(arr,obj, new Random().nextInt(to-from)+from);
	}
	public static <T> T[] invert(T[] arr){
		int i = arr.length/2;
		while(i>0){
			T o = arr[arr.length-i];
			arr[arr.length-i]=arr[--i];
			arr[i]=o;
		}
		return arr;
	}
	
	//int
	public static int[] expand(int[] arr, int i){
		return Arrays.copyOf(arr, arr.length+i);
	}
	public static int[] addAndExpand(int[] arr, int obj) {
		arr = expand(arr, 1);
		arr[arr.length-1] = obj;
		return arr;
	}
	public static int[] addAndExpand(int[] arr, int obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, 1);
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i-1];
		arr[index]=obj;
		return arr;
	}
	public static int[] addArrayAndExpand(int[] arr, int[] obj) {
		int len = arr.length;
		arr = expand(arr, obj.length);
		for(int i = 0;i< obj.length;i++){
			arr[len+i]=obj[i];
		}
		return arr;
	}
	public static int[] addArrayAndExpand(int[] arr, int[] obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, obj.length);
		index+=obj.length-1;
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i- obj.length];
		index-= obj.length-1;
		for(int i = 0;i<obj.length;i++)arr[index+i]=obj[i];
		return arr;
	}
	public static int[] shrink(int[] arr, int i) {
		return Arrays.copyOf(arr, arr.length-i);
	}
	public static int[] removeAndShrink(int[] arr, int i) {
		if(i>=arr.length||i<0)throw new IllegalArgumentException(i+" isn't in range 0 - "+arr.length);
		for(i = i+1;i<arr.length;i++)arr[i-1]=arr[i];
		return shrink(arr, 1);
	}
	public static int[] shuffle(int[] arr) {
		Random r = new Random();
		int ri;
		int obj;
		for(int i = 0;i<arr.length;i++) {
			ri=r.nextInt(arr.length);
			obj = arr[i];
			arr[i]=arr[ri];
			arr[ri]=obj;
		}
		return arr;
	}
	public static int[] shuffleInTo(int[] arr,int obj) {
		return addAndExpand(arr,obj, new Random().nextInt(arr.length+1));
	}
	public static int[] shuffleInTo(int[] arr,int obj,int from, int to) {
		if(to>arr.length+1||to<=from)throw new IllegalArgumentException("Invalid range");
		return addAndExpand(arr,obj, new Random().nextInt(to-from)+from);
	}
	public static int[] invert(int[] arr){
		int i = arr.length/2;
		while(i>0){
			int o = arr[arr.length-i];
			arr[arr.length-i]=arr[--i];
			arr[i]=o;
		}
		return arr;
	}
	
	//long
	public static long[] expand(long[] arr, int i){
		return Arrays.copyOf(arr, arr.length+i);
	}
	public static long[] addAndExpand(long[] arr, long obj) {
		arr = expand(arr, 1);
		arr[arr.length-1] = obj;
		return arr;
	}
	public static long[] addAndExpand(long[] arr, long obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, 1);
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i-1];
		arr[index]=obj;
		return arr;
	}
	public static long[] addArrayAndExpand(long[] arr, long[] obj) {
		int len = arr.length;
		arr = expand(arr, obj.length);
		for(int i = 0;i< obj.length;i++){
			arr[len+i]=obj[i];
		}
		return arr;
	}
	public static long[] addArrayAndExpand(long[] arr, long[] obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, obj.length);
		index+=obj.length-1;
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i- obj.length];
		index-= obj.length-1;
		for(int i = 0;i<obj.length;i++)arr[index+i]=obj[i];
		return arr;
	}
	public static long[] shrink(long[] arr, int i) {
		return Arrays.copyOf(arr, arr.length-i);
	}
	public static long[] removeAndShrink(long[] arr, int i) {
		if(i>=arr.length||i<0)throw new IllegalArgumentException(i+" isn't in range 0 - "+arr.length);
		for(i = i+1;i<arr.length;i++)arr[i-1]=arr[i];
		return shrink(arr, 1);
	}
	public static long[] shuffle(long[] arr) {
		Random r = new Random();
		int ri;
		long obj;
		for(int i = 0;i<arr.length;i++) {
			ri=r.nextInt(arr.length);
			obj = arr[i];
			arr[i]=arr[ri];
			arr[ri]=obj;
		}
		return arr;
	}
	public static long[] shuffleInTo(long[] arr,long obj) {
		return addAndExpand(arr,obj, new Random().nextInt(arr.length+1));
	}
	public static long[] shuffleInTo(long[] arr,long obj,int from, int to) {
		if(to>arr.length+1||to<=from)throw new IllegalArgumentException("Invalid range");
		return addAndExpand(arr,obj, new Random().nextInt(to-from)+from);
	}
	public static long[] invert(long[] arr){
		int i = arr.length/2;
		while(i>0){
			long o = arr[arr.length-i];
			arr[arr.length-i]=arr[--i];
			arr[i]=o;
		}
		return arr;
	}
	
	//short
	public static short[] expand(short[] arr, int i){
		return Arrays.copyOf(arr, arr.length+i);
	}
	public static short[] addAndExpand(short[] arr, short obj) {
		arr = expand(arr, 1);
		arr[arr.length-1] = obj;
		return arr;
	}
	public static short[] addAndExpand(short[] arr, short obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, 1);
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i-1];
		arr[index]=obj;
		return arr;
	}
	public static short[] addArrayAndExpand(short[] arr, short[] obj) {
		int len = arr.length;
		arr = expand(arr, obj.length);
		for(int i = 0;i< obj.length;i++){
			arr[len+i]=obj[i];
		}
		return arr;
	}
	public static short[] addArrayAndExpand(short[] arr, short[] obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, obj.length);
		index+=obj.length-1;
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i- obj.length];
		index-= obj.length-1;
		for(int i = 0;i<obj.length;i++)arr[index+i]=obj[i];
		return arr;
	}
	public static short[] shrink(short[] arr, int i) {
		return Arrays.copyOf(arr, arr.length-i);
	}
	public static short[] removeAndShrink(short[] arr, int i) {
		if(i>=arr.length||i<0)throw new IllegalArgumentException(i+" isn't in range 0 - "+arr.length);
		for(i = i+1;i<arr.length;i++)arr[i-1]=arr[i];
		return shrink(arr, 1);
	}
	public static short[] shuffle(short[] arr) {
		Random r = new Random();
		int ri;
		short obj;
		for(int i = 0;i<arr.length;i++) {
			ri=r.nextInt(arr.length);
			obj = arr[i];
			arr[i]=arr[ri];
			arr[ri]=obj;
		}
		return arr;
	}
	public static short[] shuffleInTo(short[] arr,short obj) {
		return addAndExpand(arr,obj, new Random().nextInt(arr.length+1));
	}
	public static short[] shuffleInTo(short[] arr,short obj,int from, int to) {
		if(to>arr.length+1||to<=from)throw new IllegalArgumentException("Invalid range");
		return addAndExpand(arr,obj, new Random().nextInt(to-from)+from);
	}
	public static short[] invert(short[] arr){
		int i = arr.length/2;
		while(i>0){
			short o = arr[arr.length-i];
			arr[arr.length-i]=arr[--i];
			arr[i]=o;
		}
		return arr;
	}
	
	
	//byte
	public static byte[] expand(byte[] arr, int i){
		return Arrays.copyOf(arr, arr.length+i);
	}
	public static byte[] addAndExpand(byte[] arr, byte obj) {
		arr = expand(arr, 1);
		arr[arr.length-1] = obj;
		return arr;
	}
	public static byte[] addAndExpand(byte[] arr, byte obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, 1);
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i-1];
		arr[index]=obj;
		return arr;
	}
	public static byte[] addArrayAndExpand(byte[] arr, byte[] obj) {
		int len = arr.length;
		arr = expand(arr, obj.length);
		for(int i = 0;i< obj.length;i++){
			arr[len+i]=obj[i];
		}
		return arr;
	}
	public static byte[] addArrayAndExpand(byte[] arr, byte[] obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, obj.length);
		index+=obj.length-1;
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i- obj.length];
		index-= obj.length-1;
		for(int i = 0;i<obj.length;i++)arr[index+i]=obj[i];
		return arr;
	}
	public static byte[] shrink(byte[] arr, int i) {
		return Arrays.copyOf(arr, arr.length-i);
	}
	public static byte[] removeAndShrink(byte[] arr, int i) {
		if(i>=arr.length||i<0)throw new IllegalArgumentException(i+" isn't in range 0 - "+arr.length);
		for(i = i+1;i<arr.length;i++)arr[i-1]=arr[i];
		return shrink(arr, 1);
	}
	public static byte[] shuffle(byte[] arr) {
		Random r = new Random();
		int ri;
		byte obj;
		for(int i = 0;i<arr.length;i++) {
			ri=r.nextInt(arr.length);
			obj = arr[i];
			arr[i]=arr[ri];
			arr[ri]=obj;
		}
		return arr;
	}
	public static byte[] shuffleInTo(byte[] arr,byte obj) {
		return addAndExpand(arr,obj, new Random().nextInt(arr.length+1));
	}
	public static byte[] shuffleInTo(byte[] arr,byte obj,int from, int to) {
		if(to>arr.length+1||to<=from)throw new IllegalArgumentException("Invalid range");
		return addAndExpand(arr,obj, new Random().nextInt(to-from)+from);
	}
	public static byte[] invert(byte[] arr){
		int i = arr.length/2;
		while(i>0){
			byte o = arr[arr.length-i];
			arr[arr.length-i]=arr[--i];
			arr[i]=o;
		}
		return arr;
	}
	
	//double
	public static double[] expand(double[] arr, int i){
		return Arrays.copyOf(arr, arr.length+i);
	}
	public static double[] addAndExpand(double[] arr, double obj) {
		arr = expand(arr, 1);
		arr[arr.length-1] = obj;
		return arr;
	}
	public static double[] addAndExpand(double[] arr, double obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, 1);
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i-1];
		arr[index]=obj;
		return arr;
	}
	public static double[] addArrayAndExpand(double[] arr, double[] obj) {
		int len = arr.length;
		arr = expand(arr, obj.length);
		for(int i = 0;i< obj.length;i++){
			arr[len+i]=obj[i];
		}
		return arr;
	}
	public static double[] addArrayAndExpand(double[] arr, double[] obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, obj.length);
		index+=obj.length-1;
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i- obj.length];
		index-= obj.length-1;
		for(int i = 0;i<obj.length;i++)arr[index+i]=obj[i];
		return arr;
	}
	public static double[] shrink(double[] arr, int i) {
		return Arrays.copyOf(arr, arr.length-i);
	}
	public static double[] removeAndShrink(double[] arr, int i) {
		if(i>=arr.length||i<0)throw new IllegalArgumentException(i+" isn't in range 0 - "+arr.length);
		for(i = i+1;i<arr.length;i++)arr[i-1]=arr[i];
		return shrink(arr, 1);
	}
	public static double[] shuffle(double[] arr) {
		Random r = new Random();
		int ri;
		double obj;
		for(int i = 0;i<arr.length;i++) {
			ri=r.nextInt(arr.length);
			obj = arr[i];
			arr[i]=arr[ri];
			arr[ri]=obj;
		}
		return arr;
	}
	public static double[] shuffleInTo(double[] arr,double obj) {
		return addAndExpand(arr,obj, new Random().nextInt(arr.length+1));
	}
	public static double[] shuffleInTo(double[] arr,double obj,int from, int to) {
		if(to>arr.length+1||to<=from)throw new IllegalArgumentException("Invalid range");
		return addAndExpand(arr,obj, new Random().nextInt(to-from)+from);
	}
	public static double[] invert(double[] arr){
		int i = arr.length/2;
		while(i>0){
			double o = arr[arr.length-i];
			arr[arr.length-i]=arr[--i];
			arr[i]=o;
		}
		return arr;
	}
	
	//float
	public static float[] expand(float[] arr, int i){
		return Arrays.copyOf(arr, arr.length+i);
	}
	public static float[] addAndExpand(float[] arr, float obj) {
		arr = expand(arr, 1);
		arr[arr.length-1] = obj;
		return arr;
	}
	public static float[] addAndExpand(float[] arr, float obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, 1);
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i-1];
		arr[index]=obj;
		return arr;
	}
	public static float[] addArrayAndExpand(float[] arr, float[] obj) {
		int len = arr.length;
		arr = expand(arr, obj.length);
		for(int i = 0;i< obj.length;i++){
			arr[len+i]=obj[i];
		}
		return arr;
	}
	public static float[] addArrayAndExpand(float[] arr, float[] obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, obj.length);
		index+=obj.length-1;
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i- obj.length];
		index-= obj.length-1;
		for(int i = 0;i<obj.length;i++)arr[index+i]=obj[i];
		return arr;
	}
	public static float[] shrink(float[] arr, int i) {
		return Arrays.copyOf(arr, arr.length-i);
	}
	public static float[] removeAndShrink(float[] arr, int i) {
		if(i>=arr.length||i<0)throw new IllegalArgumentException(i+" isn't in range 0 - "+arr.length);
		for(i = i+1;i<arr.length;i++)arr[i-1]=arr[i];
		return shrink(arr, 1);
	}
	public static float[] shuffle(float[] arr) {
		Random r = new Random();
		int ri;
		float obj;
		for(int i = 0;i<arr.length;i++) {
			ri=r.nextInt(arr.length);
			obj = arr[i];
			arr[i]=arr[ri];
			arr[ri]=obj;
		}
		return arr;
	}
	public static float[] shuffleInTo(float[] arr,float obj) {
		return addAndExpand(arr,obj, new Random().nextInt(arr.length+1));
	}
	public static float[] shuffleInTo(float[] arr,float obj,int from, int to) {
		if(to>arr.length+1||to<=from)throw new IllegalArgumentException("Invalid range");
		return addAndExpand(arr,obj, new Random().nextInt(to-from)+from);
	}
	public static float[] invert(float[] arr){
		int i = arr.length/2;
		while(i>0){
			float o = arr[arr.length-i];
			arr[arr.length-i]=arr[--i];
			arr[i]=o;
		}
		return arr;
	}
	
	//char
	public static char[] expand(char[] arr, int i){
		return Arrays.copyOf(arr, arr.length+i);
	}
	public static char[] addAndExpand(char[] arr, char obj) {
		arr = expand(arr, 1);
		arr[arr.length-1] = obj;
		return arr;
	}
	public static char[] addAndExpand(char[] arr, char obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, 1);
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i-1];
		arr[index]=obj;
		return arr;
	}
	public static char[] addArrayAndExpand(char[] arr, char[] obj) {
		int len = arr.length;
		arr = expand(arr, obj.length);
		for(int i = 0;i< obj.length;i++){
			arr[len+i]=obj[i];
		}
		return arr;
	}
	public static char[] addArrayAndExpand(char[] arr, char[] obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, obj.length);
		index+=obj.length-1;
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i- obj.length];
		index-= obj.length-1;
		for(int i = 0;i<obj.length;i++)arr[index+i]=obj[i];
		return arr;
	}
	public static char[] shrink(char[] arr, int i) {
		return Arrays.copyOf(arr, arr.length-i);
	}
	public static char[] removeAndShrink(char[] arr, int i) {
		if(i>=arr.length||i<0)throw new IllegalArgumentException(i+" isn't in range 0 - "+arr.length);
		for(i = i+1;i<arr.length;i++)arr[i-1]=arr[i];
		return shrink(arr, 1);
	}
	public static char[] shuffle(char[] arr) {
		Random r = new Random();
		int ri;
		char obj;
		for(int i = 0;i<arr.length;i++) {
			ri=r.nextInt(arr.length);
			obj = arr[i];
			arr[i]=arr[ri];
			arr[ri]=obj;
		}
		return arr;
	}
	public static char[] shuffleInTo(char[] arr,char obj) {
		return addAndExpand(arr,obj, new Random().nextInt(arr.length+1));
	}
	public static char[] shuffleInTo(char[] arr,char obj,int from, int to) {
		if(to>arr.length+1||to<=from)throw new IllegalArgumentException("Invalid range");
		return addAndExpand(arr,obj, new Random().nextInt(to-from)+from);
	}
	public static char[] invert(char[] arr){
		int i = arr.length/2;
		while(i>0){
			char o = arr[arr.length-i];
			arr[arr.length-i]=arr[--i];
			arr[i]=o;
		}
		return arr;
	}
	
	//boolean
	public static boolean[] expand(boolean[] arr, int i){
		return Arrays.copyOf(arr, arr.length+i);
	}
	public static boolean[] addAndExpand(boolean[] arr, boolean obj) {
		arr = expand(arr, 1);
		arr[arr.length-1] = obj;
		return arr;
	}
	public static boolean[] addAndExpand(boolean[] arr, boolean obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, 1);
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i-1];
		arr[index]=obj;
		return arr;
	}
	public static boolean[] addArrayAndExpand(boolean[] arr, boolean[] obj) {
		int len = arr.length;
		arr = expand(arr, obj.length);
		for(int i = 0;i< obj.length;i++){
			arr[len+i]=obj[i];
		}
		return arr;
	}
	public static boolean[] addArrayAndExpand(boolean[] arr, boolean[] obj,int index) {
		if(index>=arr.length+1||index<0)throw new IllegalArgumentException(index+" isn't in range 0 - "+arr.length+1);
		arr = expand(arr, obj.length);
		index+=obj.length-1;
		for(int i = arr.length-1;i>index;i--)arr[i]=arr[i- obj.length];
		index-= obj.length-1;
		for(int i = 0;i<obj.length;i++)arr[index+i]=obj[i];
		return arr;
	}
	public static boolean[] shrink(boolean[] arr, int i) {
		return Arrays.copyOf(arr, arr.length-i);
	}
	public static boolean[] removeAndShrink(boolean[] arr, int i) {
		if(i>=arr.length||i<0)throw new IllegalArgumentException(i+" isn't in range 0 - "+arr.length);
		for(i = i+1;i<arr.length;i++)arr[i-1]=arr[i];
		return shrink(arr, 1);
	}
	public static boolean[] shuffle(boolean[] arr) {
		Random r = new Random();
		int ri;
		boolean obj;
		for (int i = 0; i < arr.length; i++) {
			ri = r.nextInt(arr.length);
			obj = arr[i];
			arr[i] = arr[ri];
			arr[ri] = obj;
		}
		return arr;
	}
	public static boolean[] shuffleInTo(boolean[] arr,boolean obj) {
		return addAndExpand(arr,obj, new Random().nextInt(arr.length+1));
	}
	public static boolean[] shuffleInTo(boolean[] arr,boolean obj,int from, int to) {
		if(to>arr.length+1||to<=from)throw new IllegalArgumentException("Invalid range");
		return addAndExpand(arr,obj, new Random().nextInt(to-from)+from);
	}
	public static boolean[] invert(boolean[] arr){
		int i = arr.length/2;
		while(i>0){
			boolean o = arr[arr.length-i];
			arr[arr.length-i]=arr[--i];
			arr[i]=o;
		}
		return arr;
	}
	
	public static int getDimension(Object array){
		return getDimension(array.getClass());
	}
	public static int getDimension(Class<?> clazz){
		if(clazz.isArray())return getDimension(clazz.getComponentType())+1;
		else return 0;
	}
	public static Class<?> getType(Object array){
		return getType(array.getClass());
	}
	public static Class<?> getType(Class<?> clazz){
		if(clazz.isArray())return getType(clazz.getComponentType());
		else return clazz;
	}
}
