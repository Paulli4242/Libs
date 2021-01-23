package net.foreverdevs.utils.io;

public class FileFormatException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -250870333170722065L;

	public FileFormatException() {
		super();
	}

	public FileFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileFormatException(String message) {
		super(message);
	}

	public FileFormatException(Throwable cause) {
		super(cause);
	}

}
