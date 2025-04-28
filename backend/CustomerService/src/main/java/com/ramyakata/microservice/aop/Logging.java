package com.ramyakata.microservice.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging execution of service methods in ProfileServiceImpl.
 * Provides logging functionality before, after successful execution, and when
 * exceptions occur in the methods of the service class.
 */
@Aspect
@Component
public class Logging {

	private static final Logger logger = LoggerFactory.getLogger(Logging.class);

	/**
	 * Pointcut to match all methods in the ProfileServiceImpl class. This pointcut
	 * is used to apply logging functionality to all public methods in the class.
	 */
	@Pointcut("execution(* com.indus.training.microservice.impl.ProfileServiceImpl.*(..))")
	public void profileServiceMethods() {
		// Pointcut definition; no implementation required.
	}

	/**
	 * Logs the entry into any method matched by the profileServiceMethods()
	 * pointcut. Provides an entry log for debugging and tracing the flow of method
	 * invocations.
	 */
	@Before("profileServiceMethods()")
	public void logBeforeMethodExecution() {
		logger.info("Entering method in ProfileServiceImpl");
	}

	/**
	 * Logs the return value of any method matched by the profileServiceMethods()
	 * pointcut. This method is invoked after the successful execution of a service
	 * method.
	 *
	 * @param result The return value of the executed method.
	 */
	@AfterReturning(pointcut = "profileServiceMethods()", returning = "result")
	public void logAfterMethodExecution(Object result) {
		logger.info("Method executed successfully, returned: {}", result);
	}

	/**
	 * Logs any exception thrown by methods matched by the profileServiceMethods()
	 * pointcut. Captures and logs the exception details for debugging and error
	 * tracking.
	 *
	 * @param ex The exception thrown by the method.
	 */
	@AfterThrowing(pointcut = "profileServiceMethods()", throwing = "ex")
	public void logException(Exception ex) {
		logger.error("Exception occurred: {}", ex.getMessage(), ex);
	}
}
