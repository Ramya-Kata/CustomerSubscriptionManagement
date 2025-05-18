package com.ramyakata.microservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Before("execution(* com.indus.training.microservice.controller..*(..))")
	public void logBeforeControllerMethods(JoinPoint joinPoint) {
		logger.info("Entering method: {}", joinPoint.getSignature().toShortString());
	}

	@After("execution(* com.indus.training.microservice.controller..*(..))")
	public void logAfterControllerMethods(JoinPoint joinPoint) {
		logger.info("Exiting method: {}", joinPoint.getSignature().toShortString());
	}

	@Before("execution(* com.indus.training.microservice.service..*(..))")
	public void logBeforeServiceMethods(JoinPoint joinPoint) {
		logger.info("Entering service method: {}", joinPoint.getSignature().toShortString());
	}

	@After("execution(* com.indus.training.microservice.service..*(..))")
	public void logAfterServiceMethods(JoinPoint joinPoint) {
		logger.info("Exiting service method: {}", joinPoint.getSignature().toShortString());
	}
}