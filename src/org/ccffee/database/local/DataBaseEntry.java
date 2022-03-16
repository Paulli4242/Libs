package org.ccffee.database.local;

import org.ccffee.utils.iteration.ArrayUtils;

import java.io.File;
import java.util.Arrays;

class DataBaseEntry implements Table{


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
	public void setData(byte[][][] data) {
		this.data = data;
	}
	public void remove(int l) {
		if(data.length>l)data = ArrayUtils.removeAndShrink(data,l);
	}
}
