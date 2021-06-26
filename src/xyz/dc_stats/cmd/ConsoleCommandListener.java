package xyz.dc_stats.cmd;

import xyz.dc_stats.logger.LogStream;
import xyz.dc_stats.logger.Logger;

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
