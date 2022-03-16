package org.ccffee.utils.exceptions;

public class CreationException extends Exception {

	public CreationException() {
		super();
	}

	public CreationException(String message, Throwable cause, boolean enableSuppression,
							 boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CreationException(String message, Throwable cause) {
		super(message, cause);
	}

	public CreationException(String message) {
		super(message);
	}

	public CreationException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 120672198847973931L;
	
}
