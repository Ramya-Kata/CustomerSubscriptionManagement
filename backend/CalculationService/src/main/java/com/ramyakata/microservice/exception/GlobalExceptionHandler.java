package com.ramyakata.microservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the Calculation Service.
 * <p>
 * This class handles all unhandled exceptions across the service, converting
 * them into well-structured HTTP responses.
 * <p>
 * Features:
 * <ul>
 * <li>Handles custom {@link CalculationException} with 400 (Bad Request)</li>
 * <li>Handles unexpected exceptions with 500 (Internal Server Error)</li>
 * <li>Logs all unexpected errors for debugging</li>
 * </ul>
 * 
 * Applied globally using {@link ControllerAdvice}.
 * 
 * @author Ramya Kata
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Handles domain-specific calculation exceptions.
	 *
	 * @param ex the thrown {@link CalculationException}
	 * @return 400 Bad Request with error message
	 */
	@ExceptionHandler(CalculationException.class)
	public ResponseEntity<String> handleCalculationException(CalculationException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	/**
	 * Handles all uncaught exceptions in the application.
	 *
	 * @param ex the unexpected exception
	 * @return 500 Internal Server Error with a user-friendly message
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
		// Log the full exception stack trace
		logger.error("An unexpected error occurred", ex);

		// Respond with a meaningful error message
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("An unexpected error occurred: " + ex.getMessage());
	}
}
