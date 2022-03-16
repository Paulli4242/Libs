package org.ccffee.cmd;

import java.io.*;

/**
 *
 * Class InputStreamCommandListener is a Thread which listens to a InputStream for commands.
 *
 */
public class InputStreamCommandListener extends Thread implements CommandSender, Closeable {
	protected CommandManager manager;
	protected InputStream in;
	protected PrintStream out;
	private boolean isRunning;

	/**
	 *
	 * Creates InputStreamCommandListener
	 *
	 * @param manager CommandManager
	 * @param in InputStream of the command source.
	 *
	 */
	public InputStreamCommandListener(CommandManager manager, InputStream in) {
		this.in = in;
		this.manager = manager;
		isRunning = true;
		start();
	}

	/**
	 *
	 * Creates InputStreamCommandListener
	 *
	 * @param manager CommandManager
	 * @param in InputStream of the command sender.
	 * @param out OutputStream to answer the command sender
	 */
	public InputStreamCommandListener(CommandManager manager, InputStream in, OutputStream out) {
		super("InputStreamListener");
		this.in = in;
		if(out instanceof PrintStream)this.out = (PrintStream) out;
		else this.out = new PrintStream(out);
		this.manager = manager;
		isRunning = true;
		start();
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
				if(!manager.onCommandReceived(this, reader.readLine().split(" ")))sendMessage("Command not found");
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

	@Override
	public void close() throws IOException {
		isRunning = false;
	}
}
