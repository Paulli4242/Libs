package net.foreverdevs.database;

public interface TableConnection {
	public byte[] getData(int l, int c);
	public byte[][] getData(int l);
	public byte[][][] getData();
	public void setData(int l, int c, byte[] d);
	public void setData(int l, byte[][] d);
	public void setData(byte[][][] d);
	public void remove(int l, int c);
	public void remove(int l);
	public void clear(int l, int c);
	public void clear(int l);
	public int getLineCount();
	public int getColumnCount(int l);
	
	public boolean isValidLine(int l);
	public boolean isValidColumn(int l, int c);
}
