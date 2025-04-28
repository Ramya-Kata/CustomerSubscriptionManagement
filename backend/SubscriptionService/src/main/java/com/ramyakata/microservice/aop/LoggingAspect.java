package com.ramyakata.microservice.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
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
public class LoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	/**
	 * Pointcut for all service methods.
	 */
	@Pointcut("execution(* com.indus.training.microservice.impl.*.*(..))")
	public void serviceMethods() {
	}

	/**
	 * Before advice for logging method entry.
	 */
	@Before("serviceMethods()")
	public void logBefore() {
		logger.info("Entering method...");
	}

	/**
	 * After returning advice for logging method exit.
	 * 
	 * @param result The result of the method execution.
	 */
	@AfterReturning(pointcut = "serviceMethods()", returning = "result")
	public void logAfterReturning(Object result) {
		logger.info("Method executed successfully. Result: {}", result);
	}

	/**
	 * After throwing advice for logging exceptions.
	 * 
	 * @param ex The exception thrown by the method.
	 */
	@AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
	public void logAfterThrowing(Exception ex) {
		logger.error("An exception occurred: {}", ex.getMessage());
	}

	/**
	 * Around advice for detailed method execution logging.
	 * 
	 * @param joinPoint The join point representing the method.
	 * @return The result of the method execution.
	 * @throws Throwable If the underlying method throws an exception.
	 */
	@Around("serviceMethods()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		logger.info("Method {} called with arguments: {}", methodName, args);

		Object result;
		try {
			result = joinPoint.proceed();
			logger.info("Method {} returned: {}", methodName, result);
		} catch (Exception ex) {
			logger.error("Method {} threw an exception: {}", methodName, ex.getMessage());
			throw ex;
		}

		return result;
	}
}