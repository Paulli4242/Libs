package xyz.dc_stats.cmd;

import xyz.dc_stats.logger.LogStream;
import xyz.dc_stats.logger.Logger;

public class ConsoleCommandListener extends InputStreamCommandListener {

	public ConsoleCommandListener(CommandManager manager) {
		super(manager, System.in, Logger.exist()?(LogStream) Logger.getLogger().addLogStream("consolecommand", new LogStream("[ConsoleCommand] ", System.out)).getLogStream("consolecommand"):System.out);
		super.t.setName("ConsoleCommandListener");
	}
}
