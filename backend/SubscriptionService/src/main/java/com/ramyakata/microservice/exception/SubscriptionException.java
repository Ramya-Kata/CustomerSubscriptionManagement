package com.ramyakata.microservice.exception;

/**
 * Description: Custom exception class for handling subscription-related errors
 * 
 */
public class SubscriptionException extends Exception {

	/**
	 * Description: Generated serial version UID for the exception class.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Description: Constructs a new SubscriptionException with the specified detail
	 * message.
	 * 
	 * @param msg The detail message that explains the cause of the exception.
	 */
	public SubscriptionException(String msg) {
		super(msg);
	}
}
