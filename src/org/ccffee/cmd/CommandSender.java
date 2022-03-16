package org.ccffee.cmd;


/**
 * Interface CommandSender represents a sender of a command.
 */
public interface CommandSender  {
	/**
	 *
	 * Sends a message to the sender of a command.
	 *
	 * @param str message.
	 */
	public void sendMessage(String str);
	/**
	 *
	 * Sends a message to the sender of a command.
	 *
	 * @param strs message.
	 */
	public default void sendMessage(String[] strs) {
		sendMessage(String.join(" ", strs));
	}
}
