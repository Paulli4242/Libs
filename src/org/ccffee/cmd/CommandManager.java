package org.ccffee.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ccffee.logger.Logger;
import org.ccffee.utils.iteration.ArrayUtils;

/**
 *
 * Class CommandManager handles commands
 *
 */
public class CommandManager {
    private ArrayList<Command> list = new ArrayList<>();

    /**
     *  Creates a CommandManager
     */
    public CommandManager() {
        list = new ArrayList<>();
    }

    /**
     *
     * Gets a command by its name or alias.
     *
     * @param alias name or alias of the command.
     * @return a command found by its name or alias, null if no matches found.
     */
    public Command getCommand(String alias) {
        for (Command cmd : list) {
            if (cmd.getName().equalsIgnoreCase(alias)) return cmd;
            for (String a : cmd.getAlias()) if (a.equalsIgnoreCase(alias)) return cmd;
        }
        return null;
    }

    /**
     *
     * Gets a list of commands.
     *
     * @return a list of commands.
     */
    public List<Command> getCommands() {
        return list;
    }

    /**
     *
     * Handles a received command.
     *
     * @param sender the sender of the command.
     * @param args command with arguments.
     * @return true if found a command, false otherwise.
     */
    public boolean onCommandReceived(CommandSender sender, String args[]) {
        Logger.getLogger().logDebug("CommandReceived Sender: %s", sender.getClass().getName());
        Command cmd;
        if ((cmd = getCommand(args[0])) != null) {
            executeCommand(cmd, sender, Arrays.copyOfRange(args, 1, args.length));
            return true;
        } else return false;
    }

    /**
     *
     * Executes a command.
     *
     * @param cmd the command to execute.
     * @param sender the sender of the command.
     * @param args arguments of the command.
     */
    protected void executeCommand(Command cmd, CommandSender sender, String[] args) {
        Logger.getLogger().logDebug("executeCommand args: %s", ArrayUtils.toString(args));
        try {
            cmd.executeCommand(sender, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Adds a command if there is no other with matching name or aliases.
     *
     * @param cmd the command to add.
     * @return true if the command was registered, false if there where another one.
     */
    public boolean registerCommand(Command cmd) {
        Logger.getLogger().logDebug("registerCommand cmd.name: %s cmd.alias", cmd.getName(), ArrayUtils.toString(cmd.getAlias()));
        if (getCommand(cmd.getName()) != null) return false;
        for (String alias : cmd.getAlias()) if (getCommand(alias) != null) return false;
        list.add(cmd);
        return true;
    }

    /**
     *
     * Adds a command and removes commands with matching names or aliases.
     *
     * @param cmd the command to add.
     */
    public void forceRegisterCommand(Command cmd) {
        Logger.getLogger().logDebug("forceRegisterCommand cmd.name: %s cmd.alias", cmd.getName(), cmd.getAlias().toString());
        Command lcmd;
        if ((lcmd = getCommand(cmd.getName())) != null) list.remove(lcmd);
        else for (String alias : cmd.getAlias())
            if ((lcmd = getCommand(alias)) != null) {
                list.remove(lcmd);
                break;
            }
        list.add(cmd);
    }

    /**
     *
     * Removes a command by its name or alias.
     *
     * @param alias name or alias of th command.
     */
    public void unregisterCommand(String alias) {
        Logger.getLogger().logDebug("unregisterCommand alias", alias);
        Command cmd;
        if ((cmd = getCommand(alias)) != null) list.remove(cmd);
    }

    /**
     *
     * Removes a command.
     *
     */
    public void unregisterCommand(Command cmd) {
        Logger.getLogger().logDebug("unregisterCommand cmd.name: %s cmd.alias", cmd.getName(), cmd.getAlias().toString());
        list.remove(cmd);
    }


}