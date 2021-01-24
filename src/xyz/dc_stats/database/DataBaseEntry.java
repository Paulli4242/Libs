package xyz.dc_stats.database;

import xyz.dc_stats.utils.iteration.ArrayUtils;

import java.io.File;
import java.util.Arrays;

public class DataBaseEntry implements TableConnection {
	
	
	public DataBaseEntry(String name, String filePath) {
		this.name = name;
		this.filePath = filePath;
	}
	private String name;
	private byte[][][] data;
	private String filePath;
	
	public String getName() {
		return name;
	}
	public byte[] getData(int l, int c) {
		if(isValidColumn(l, c))return data[l][c];
		return null;
	}
	@Override
	public byte[][] getData(int l) {
		if(isValidLine(l))return data[l];
		else return null;
	}
	public byte[][][] getData() {
		return data;
	}
	@Override
	public int getColumnCount(int l) {
		return isValidLine(l)?data[l].length:0;
	}
	@Override
	public int getLineCount() {
		return data.length;
	}

	public File getFile() {
		return new File(filePath);
	}
	public File getFile(File dataFolder) {
		return new File(dataFolder, filePath);
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

	@Override
	public void remove(int l, int c) {
		if(isValidColumn(l,c))data[l] = ArrayUtils.removeAndShrink(data[l],c);
	}

	@Override
	public void remove(int l) {
		if(isValidLine(l))data = ArrayUtils.removeAndShrink(data,l);
	}

	@Override
	public void clear(int l, int c) {
		if(isValidColumn(l,c))data[l][c]=null;
	}

	@Override
	public void clear(int l) {
		if(isValidLine(l))data[l] = new byte[0][];
	}

	public boolean isValidLine(int l) {
		return data.length>l;
	}
	public boolean isValidColumn(int l, int c) {
		return isValidLine(l)&&data[l].length>c;
	}
	
}
