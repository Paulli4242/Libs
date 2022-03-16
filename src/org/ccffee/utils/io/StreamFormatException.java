package org.ccffee.utils.io;


import java.io.IOException;

public class StreamFormatException extends IOException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -250870333170722065L;

	public StreamFormatException() {
		super();
	}

	public StreamFormatException(String message) {
		super(message);
	}

	public StreamFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public StreamFormatException(Throwable cause) {
		super(cause);
	}
}
