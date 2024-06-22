package com.ankit.exception;

public class FileProcessingException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5787961821812461757L;

	public FileProcessingException(String message) {
        super(message);
    }

    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}