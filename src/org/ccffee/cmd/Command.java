package org.ccffee.cmd;

/**
 *
 * Class Command represents a Command
 *
 */
public abstract class Command implements ICommand {
	protected String name;
	protected String[] alias;

	/**
	 *
	 * Creates a new Command with a Name and 0 o more aliases.
	 *
	 * @param name the name of the command.
	 * @param alias aliases of the command.
	 */
	public Command(String name, String...alias) {
		this.name=name;
		this.alias = alias;
	}

	/**
	 *
	 * Gets the name of this command.
	 *
	 * @return the name of this command.
	 */
	@Override
	public String getName() {
		return name;
	}
	/**
	 *
	 * Gets all aliases of this command.
	 *
	 * @return all aliases of this command.
	 */
	@Override
	public String[] getAlias() {
		return alias;
	}
}
