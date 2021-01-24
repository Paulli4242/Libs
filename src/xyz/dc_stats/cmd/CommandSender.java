package xyz.dc_stats.cmd;

public interface CommandSender  {
	
	public void sendMessage(String str);
	public default void sendMessage(String[] strs) {
		sendMessage(String.join(" ", strs));
	}
}
