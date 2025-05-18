package com.ramyakata.microservice.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLog {
	@Pointcut("execution(* com.indus.training.microservice.impl.AccountDao.*(..))")
	public void accountServiceMethods() {
	}

	@Before("accountServiceMethods()")
	public void logBefore(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		System.out.println("Before method: " + methodName + ", with arguments: " + Arrays.toString(arguments));
	}

	@After("accountServiceMethods()")
	public void logAfter(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("After method: " + methodName);
	}

	@AfterReturning(value = "accountServiceMethods()", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("After method: " + methodName + " successfully executed, returned: " + result);
	}

	@AfterThrowing(value = "accountServiceMethods()", throwing = "exception")
	public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("After method: " + methodName + " threw an exception: " + exception.getMessage());
	}
}
