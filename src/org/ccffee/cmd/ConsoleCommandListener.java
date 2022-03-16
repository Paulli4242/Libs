package org.ccffee.cmd;

import org.ccffee.logger.LogStream;
import org.ccffee.logger.Logger;

/**
 *
 * Class InputStreamCommandListener is a Thread which listens to the console for commands.
 *
 */
public class ConsoleCommandListener extends InputStreamCommandListener {

	/**
	 *
	 * Creates a CommandListener for the console.
	 *
	 * @param manager CommandManager.
	 */

	public ConsoleCommandListener(CommandManager manager) {
		super(manager, System.in, Logger.exist()?(LogStream) Logger.getLogger().addLogStream("consolecommand", new LogStream("[ConsoleCommand] ", System.out)).getLogStream("consolecommand"):System.out);
		setName("ConsoleCommandListener");
	}
}
