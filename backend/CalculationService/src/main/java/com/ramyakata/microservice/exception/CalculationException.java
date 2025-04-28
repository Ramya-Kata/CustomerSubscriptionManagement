package com.ramyakata.microservice.exception;

/**
 * Description: Custom Exception for the CalculationService
 */
public class CalculationException extends Exception{

	/**
	 * Description: A unique identifier for the exception class to ensure proper serialization and deserialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Description: Constructor for the Custom Exception
	 * @param msg: The error message describing the cause of the exception. 
	 */
	public CalculationException(String msg) {
		super(msg);
	}
}
