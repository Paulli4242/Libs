package xyz.dc_stats.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xyz.dc_stats.logger.Logger;
import xyz.dc_stats.utils.iteration.ArrayUtils;

public class CommandManager {
	
	ArrayList<Command> list = new ArrayList<>();
	
	public CommandManager() {
		list = new ArrayList<>();
	}
	public Command getCommand(String alias) {
		for(Command cmd : list) {
			if(cmd.getName().equalsIgnoreCase(alias))return cmd;
			for(String a:cmd.getAlias())if(a.equalsIgnoreCase(alias))return cmd;
		}
		return null;
	}
	public List<Command> getCommands(){
		return list;
	}
	public boolean onCommandRecived(CommandSender sender, String args[]) {
		Logger.getLogger().logDebug("CommdandRecived Sender: %s",sender.getClass().getName());
		Command cmd;
		if((cmd=getCommand(args[0])) != null) {
			executeCommand(cmd, sender, Arrays.copyOfRange(args, 1, args.length));
			return true;
		}else return false;
	}
	protected void executeCommand(Command cmd, CommandSender sender, String[] args) {
		Logger.getLogger().logDebug("executeCommand args: %s", ArrayUtils.toString(args));
		try{
			cmd.executeCommand(sender, args);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean registerCommand(Command cmd){
		Logger.getLogger().logDebug("registerCommand cmd.name: %s cmd.alias",cmd.getName(), ArrayUtils.toString(cmd.getAlias()));
		if(getCommand(cmd.getName())!=null)return false;
		for(String alias:cmd.getAlias())if(getCommand(alias)!=null)return false;
		list.add(cmd);
		return true;
	}
	public void forceRegisterCommand(Command cmd) {
		Logger.getLogger().logDebug("forceRegisterCommand cmd.name: %s cmd.alias",cmd.getName(),cmd.getAlias().toString());
		Command lcmd;
		if((lcmd =getCommand(cmd.getName()))!=null)list.remove(lcmd);
		else for(String alias:cmd.getAlias())if((lcmd =getCommand(alias))!=null) {
			list.remove(lcmd);
			break;
		}
		list.add(cmd);
	}
	public void unregisterCommand(String alias) {
		Logger.getLogger().logDebug("unregisterCommand alias",alias);
		Command cmd;
		if((cmd = getCommand(alias))!=null)list.remove(cmd);
	}
	public void unregisterCommand(Command cmd) {
		Logger.getLogger().logDebug("unregisterCommand cmd.name: %s cmd.alias",cmd.getName(),cmd.getAlias().toString());
		list.remove(cmd);
	}
	
	
}