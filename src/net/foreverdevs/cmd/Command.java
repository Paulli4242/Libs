package net.foreverdevs.cmd;

public abstract class Command {
	protected String name;
	protected String[] alias;
	
	public Command(String name, String...alias) {
		this.name=name;
		this.alias = alias;
	}
	public abstract void executeCommand(CommandSender sender, String[] args);

	public String getName() {
		return name;
	}
	public String[] getAlias() {
		return alias;
	}
}
