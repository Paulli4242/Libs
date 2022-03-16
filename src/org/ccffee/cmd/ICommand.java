package org.ccffee.cmd;

/**
 *
 * Interface ICommand represents a Command
 *
 */
public interface ICommand {

	/**
	 *
	 * Gets the name of the command.
	 *
	 * @return the name of the command.
	 */
	String getName();
	default String[] getAlias(){
		return new String[0];
	}
	void executeCommand(CommandSender sender, String[] args);

}
