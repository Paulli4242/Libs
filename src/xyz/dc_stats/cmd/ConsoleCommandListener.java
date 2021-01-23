package net.foreverdevs.cmd;

import net.foreverdevs.logger.LogStream;
import net.foreverdevs.logger.Logger;

public class ConsoleCommandListener extends InputStreamCommandListener {

	public ConsoleCommandListener(CommandManager manager) {
		super(manager, System.in, Logger.exist()?(LogStream) Logger.getLogger().addLogStream("consolecommand", new LogStream("[ConsoleCommand] ", System.out)).getLogStream("consolecommand"):System.out);
		super.t.setName("ConsoleCommandListener");
	}
}
