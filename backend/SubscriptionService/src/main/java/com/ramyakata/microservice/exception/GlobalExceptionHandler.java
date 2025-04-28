package com.ramyakata.microservice.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.ramyakata.microservice.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Description: Handles SubscriptionException and returns an appropriate
	 * response.
	 *
	 * @param ex      The SubscriptionException instance.
	 * @param request The WebRequest containing details of the HTTP request.
	 * @return A structured error response.
	 */
	@ExceptionHandler(SubscriptionException.class)
	public ResponseEntity<ErrorResponse> handleSubscriptionException(SubscriptionException ex, WebRequest request) {
		ErrorResponse response = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Description: Handles generic exceptions and returns an appropriate response.
	 *
	 * @param ex      The Exception instance.
	 * @param request The WebRequest containing details of the HTTP request.
	 * @return A structured error response.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
		ErrorResponse response = new ErrorResponse(LocalDateTime.now(), "An unexpected error occurred",
				request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}