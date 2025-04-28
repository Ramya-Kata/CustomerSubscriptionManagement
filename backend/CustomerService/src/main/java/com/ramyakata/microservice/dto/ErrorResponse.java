package com.ramyakata.microservice.dto;

/**
 * Description: Class representing a standardized error response. Contains the
 * HTTP status code and an error message.
 */
public class ErrorResponse {

	private int statusCode;
	private String message;

	/**
	 * Description: Constructs an ErrorResponse with the given status code and
	 * message.
	 *
	 * @param statusCode The HTTP status code.
	 * @param message    The error message.
	 */
	public ErrorResponse(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
