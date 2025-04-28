package com.ramyakata.microservice.exception;

/**
 * Custom exception class for handling errors related to customer operations.
 * Extends the base Exception class to provide meaningful error messages.
 */
public class CustomerException extends Exception {

	/**
	 * Description: Unique identifier for the serialized object.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Description: Constructs a new CustomerException with the specified detail
	 * message.
	 *
	 * @param msg The detail message for the exception.
	 */
	public CustomerException(String msg) {
		super(msg);
	}
}
