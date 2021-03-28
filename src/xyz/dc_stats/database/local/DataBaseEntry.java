package xyz.dc_stats.database.local;

import xyz.dc_stats.utils.iteration.ArrayUtils;

import java.io.File;
import java.util.Arrays;

public class DataBaseEntry{


	public DataBaseEntry(String name) {
		this.name = name;
	}
	private String name;
	private byte[][][] data;

	public String getName() {
		return name;
	}
	public byte[][][] getData() {
		return data;
	}
	public File getFile() {
		return new File(name+".dat");
	}
	public File getFile(File dataFolder) {
		return new File(dataFolder, name+".dat");
	}
	public void setData(int l, int c,byte[] d) {
		if(data.length<=l) {
			data = Arrays.copyOf(data, l+1);
			data[l] = new byte[c][];
		}
		if(data[l].length<=c)data[l] = Arrays.copyOf(data[l], c+1);
		data[l][c] = d;
	}
	public void setData(int l, byte[][] d) {
		if(data.length<=l)data = Arrays.copyOf(data, l+1);
		data[l] = d;
	}
	public void setData(byte[][][] data) {
		this.data = data;
	}
	public void remove(int l, int c) {
		if(isValidColumn(l,c))data[l] = ArrayUtils.removeAndShrink(data[l],c);
	}
	public void remove(int l) {
		if(isValidLine(l))data = ArrayUtils.removeAndShrink(data,l);
	}
	public void clear(int l, int c) {
		if(isValidColumn(l,c))data[l][c]=null;
	}
	public void clear(int l) {
		if(isValidLine(l))data[l] = new byte[0][];
	}
	public boolean isValidLine(int l) {
		return data.length>l;
	}
	public boolean isValidColumn(int l, int c) {
		return isValidLine(l) && data[l].length > c;
	}
}
