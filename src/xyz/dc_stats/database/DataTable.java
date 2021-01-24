package net.foreverdevs.database;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import net.foreverdevs.utils.io.ByteUtils;

public class DataTable {
	TableConnection data;
	public DataTable(TableConnection data) {
		this.data = data;
	}

	/**
	 * sets a <code>byte[] d</code> to the table on line l and column c.
	 */
	public void set(int l, int c, byte[] d) {
		data.setData(l, c, d);
	}
	/**
	 * sets a <code>{@link String} d</code> to the table on line l and column c.
	 */
	public void set(int l, int c, String d) {
		set(l, c, ByteUtils.stringToBytes(d));
	}
	/**
	 * sets a <code>char d</code> to the table on line l and column c.
	 */
	public void set(int l, int c, char d) {
		set(l,c,ByteUtils.charToBytes(d));
	}
	/**
	 * sets a <code>{@link Number} d</code>(long, int, short, byte, double, float) to the table on line l and column c.
	 */
	public void set(int l, int c, Number d) {
		set(l, c, ByteUtils.numberToBytes(d));
	}
	/**
	 * sets a <code>boolean d</code> to the table on line l and column c
	 */
	public void set(int l,int c, boolean d) {
		set(l, c, ByteUtils.boolToBytes(d));
	}
	/**
	 * sets <code>{@link ByteConvertable} d</code> to the table on line l and column c.
	 */
	public void set(int l, int c, ByteConvertable d) {
		set(l,c,d.toByteArray());
	}

	/**
	 * @return <code>byte[]</code> which is  in line l and column c
	 */
	public byte[] getBytes(int l, int c) {
		return data.getData(l, c);
	}

	/**
	 * tries to convert <code>byte[]</code> from <code>getBytes(l,c)</code> to <code>{@link String}</code>
	 */
	public String getString(int l, int c) {
		return ByteUtils.bytesToString(getBytes(l, c));
	}
	/**
	 * tries to convert <code>byte[]</code> from <code>getBytes(l,c)</code> to <code>char</code>
	 */
	public char getChar(int l, int c) {
		if(getBytes(l, c)==null)throw new NullPointerException();
		return ByteUtils.bytesToChar(getBytes(l, c));
	}
	/**
	 * tries to convert <code>byte[]</code> from <code>getBytes(l,c)</code> to <code>int</code>
	 */
	public int getInt(int l, int c) {
		if(getBytes(l, c)==null)throw new NullPointerException();
		return ByteUtils.bytesToInt(getBytes(l, c));
	}
	/**
	 * tries to convert <code>byte[]</code> from <code>getBytes(l,c)</code> to <code>long</code>
	 */
	public long getLong(int l, int c) {
		if(getBytes(l, c)==null)throw new NullPointerException();
		return ByteUtils.bytesToLong(getBytes(l, c));
	}
	/**
	 * tries to convert <code>byte[]</code> from <code>getBytes(l,c)</code> to <code>double</code>
	 */
	public double getDouble(int l, int c) {
		if(getBytes(l, c)==null)throw new NullPointerException();
		return ByteUtils.bytesToDouble(getBytes(l, c));
	}
	/**
	 * tries to convert <code>byte[]</code> from <code>getBytes(l,c)</code> to <code>short</code>
	 */
	public short getShort(int l, int c) {
		return ByteUtils.bytesToShort(getBytes(l, c));
	}
	/**
	 * tries to convert <code>byte[]</code> from <code>getBytes(l,c)</code> to <code>float</code>
	 */
	public float getFloat(int l, int c) {
		return ByteUtils.bytesToFloat(getBytes(l, c));
	}
	/**
	 * tries to convert <code>byte[]</code> from <code>getBytes(l,c)</code> to <code>boolean</code>
	 */
	public boolean getBoolean(int l, int c) {
		if(getBytes(l, c)==null||getBytes(l, c).length<1)throw new NullPointerException();
		return ByteUtils.bytesToBoolean(getBytes(l, c));
	}


	@SuppressWarnings("unchecked")
	/**
	 * tries to get <code>T</code> by a constructor or static method with the argument <code>byte[]</code>
	 */
	public <T> T get(Class<T> clazz, int l, int c){
		if(getBytes(l, c)==null)throw new NullPointerException();
		try {
			Constructor<T> cs = clazz.getDeclaredConstructor(byte[].class);
			cs.setAccessible(true);
			return cs.newInstance(new Object[] {getBytes(l, c)});
		} catch (InvocationTargetException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException e) {
		}
		for(Method m : clazz.getDeclaredMethods()) {
			if(m.getReturnType().equals(clazz)&&m.getParameterTypes().length==1&&m.getParameterTypes()[0].equals(byte[].class)&&Modifier.isStatic(m.getModifiers()))
				try {
					m.setAccessible(true);
					return (T) m.invoke(null, getBytes(l, c));
				}catch (SecurityException | InvocationTargetException | IllegalAccessException | IllegalArgumentException e){
				}
		}
		return null;
	}
	public int getColumnCount(int l){
		return data.getColumnCount(l);
	}
	public void remove(int l, int c){
		data.remove(l,c);
	}
	public void clear(int l, int c){
		data.clear(l,c);
	}
	/**
	 * sets <code>byte[][] d</code>as line l
	 */
	public void setLine(int l, byte[][] d) {
		data.setData(l, d);
	}

	/**
	 * sets  <code>{@link LineByteConvertable} d</code> as line l
	 */
	public void setLine(int l, LineByteConvertable d) {
		setLine(l, d.to2DByteArray());
	}

	/**
	 * @return <code>byte[][]</code> from line l
	 */
	public byte[][] getLine(int l) {
		return data.getData(l);
	}
	@SuppressWarnings("unchecked")
	/**
	 * tries to get <code>T</code> by a constructor or static method with the argument <code>byte[][]</code>
	 */
	public <T> T getLine(Class<T> clazz, int l) {
		if(!isValidLine(l))throw new NullPointerException();
		try {
			Constructor<T> cs = clazz.getDeclaredConstructor(byte[][].class);
			cs.setAccessible(true);
			return cs.newInstance(new Object[] {getLine(l)});
		}catch (InvocationTargetException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException e) {
		}
		for(Method m : clazz.getDeclaredMethods()) {
			if(m.getReturnType().equals(clazz)&&m.getParameterTypes().length==1&&m.getParameterTypes()[0].equals(byte[][].class)&&Modifier.isStatic(m.getModifiers()))
				try {
					m.setAccessible(true);
					return (T) m.invoke(null, new Object[] {getLine(l)});
				}catch (SecurityException | InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
				}
		}
		return null;
	}
	public int getLineCount(){
		return data.getLineCount();
	}
	public void removeLine(int l){
		data.remove(l);
	}
	public void clearLine(int l){
		data.clear(l);
	}
	/**
	 * sets <code>byte[][][] d</code> as table
	 */
	public void setTable(byte [][][] d) {
		data.setData(d);
	}

	/**
	 * sets <code>{@link TableByteConvertable} d </code> as table
	 */
	public void setTable(TableByteConvertable d) {
		setTable(d.to3DByteArray());
	}

	/**
	 * @return the whole Table as <code>byte[][][]</code>
	 */
	public byte[][][] getTable(){
		return data.getData();
	}
	/**
	 * tries to get <code>T</code> by a constructor or static method with the argument <code>byte[][][]</code>
	 */
	@SuppressWarnings("unchecked")
	public <T> T getTable(Class<T> clazz){
		try {
			Constructor<T> cs = clazz.getDeclaredConstructor(byte[][][].class);
			cs.setAccessible(true);
			return cs.newInstance(new Object[] {data});
		} catch (InvocationTargetException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException e) {
		}
		for(Method m : clazz.getDeclaredMethods()) {
			if(m.getReturnType().equals(clazz)&&m.getParameterTypes().length==1&&m.getParameterTypes()[0].equals(byte[][][].class)&&Modifier.isStatic(m.getModifiers()))
				try {
					m.setAccessible(true);
					return (T) m.invoke(null, new Object[] {data});
				} catch (SecurityException | InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
				}
		}
		return null;
	}

	/**
	 * checks if Line <code>l</code> is valid
	 */
	public boolean isValidLine(int l) {
		return data.isValidLine(l);
	}

	/**
	 *checks if Column <code>c</code> in line <code>l</code> is valid
	 */
	public boolean isValidColumn(int l, int c) {
		return data.isValidColumn(l, c);
	}

	public boolean isSet(int l, int c) {
		return isValidColumn(l, c)&&data.getData(l, c)!=null && data.getData(l, c).length>0;
	}
	public void resetTable() {
		data.setData(new byte[0][0][0]);
	}
	public static boolean isSet(int c, byte[][] d) {
		return d.length>c&&d[c]!=null&&d[c].length>0;
	}
	public static boolean isSet(int l,int c, byte[][][] d) {
		return d.length>l&&isSet(c, d[l]);
	}
}
