package xyz.dc_stats.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

public class InputStreamCommandListener implements CommandSender, Runnable{
	protected Thread t;
	protected CommandManager manager;
	protected InputStream in;
	protected PrintStream out;
	private boolean isRunning;
	
	public InputStreamCommandListener(CommandManager manager, InputStream in) {
		this.in = in;
		this.manager = manager;
		isRunning = true;
		t = new Thread(this,"InputStreamListener");
		t.setDaemon(true);
		t.start();
	}
	public InputStreamCommandListener(CommandManager manager, InputStream in, OutputStream out) {
		this.in = in;
		if(out instanceof PrintStream)this.out = (PrintStream) out;
		else this.out = new PrintStream(out);
		this.manager = manager;
		isRunning = true;
		t = new Thread(this,"InputStreamListener");
		t.setDaemon(true);
		t.start();
	}
	
	@Override
	public void sendMessage(String str) {
		if(out!=null)out.println(str);
	}
	@Override
	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		while(isRunning) {
			try {
				if(!manager.onCommandRecived(this, reader.readLine().split(" ")))sendMessage("Command not found");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
