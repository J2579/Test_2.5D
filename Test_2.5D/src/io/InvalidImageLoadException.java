package io;

/**
 * An exception to be thrown when loading an image asset fails.
 * 
 * @author J2579
 */
public class InvalidImageLoadException extends Exception {

	/** Serial ID */
	private static final long serialVersionUID = 1L;
	
	/** Default Message */
	private static final String DEFAULT_MESSAGE = "Invalid Image File";
	
	/**
	 * Constructs an InvalidImageLoadException with the default message.
	 */
	public InvalidImageLoadException() {
		super(DEFAULT_MESSAGE);
	}
	
	/**
	 * Constructs an InvalidImageLoadException with a specified
	 * 
	 * @param message The message of the exception
	 */
	public InvalidImageLoadException(String message) {
		super(message);
	}
}