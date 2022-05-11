package com.smart.exceptions;

public class OperationFailedException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OperationFailedException() {
    }

    public OperationFailedException(String message) {
        super(message);
    }
}
