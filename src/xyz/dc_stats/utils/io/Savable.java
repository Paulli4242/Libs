package xyz.dc_stats.utils.io;

public interface Savable extends Runnable {
	public default void  run() {
		save();
	}
	public void save();
}
