package org.ccffee.utils.functional;

import java.util.function.Consumer;

public abstract class Waiter<T>{
	
	public abstract T complete();
	public abstract void submit();
	public void asynchronComplete(Consumer<T> consumer) {
		new Thread(()->consumer.accept(complete())).start();
	}
	
	public class AsynchronCompleter extends Thread {
		
		Consumer<T> consumer;
		
		public AsynchronCompleter(Consumer<T> consumer) {
			this.consumer = consumer;
			setDaemon(true);
			start();
		}
		@Override
		public void run() {
			consumer.accept(complete());
		}
	}
	
}
