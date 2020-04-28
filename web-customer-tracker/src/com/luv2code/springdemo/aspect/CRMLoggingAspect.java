package com.luv2code.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	// setup logger
	private Logger myLogger = Logger.getLogger(this.getClass().getName());

	// setup pointcut declaration
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {
	}

	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {
	}

	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDAOPackage() {
	}

	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow() {
	}

	// add @Before avdice
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {

		// display the method we are calling
		String method = joinPoint.getSignature().toShortString();
		myLogger.info(">>>> in @Before: calling method : " + method);

		// display the arguments to the methods
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			myLogger.info(">>>> argument : " + arg);
		}
	}

	// add @AfterReturning advice
	@AfterReturning(pointcut = "forAppFlow()", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {

		// display the method we are calling
		String method = joinPoint.getSignature().toShortString();
		myLogger.info(">>>> in @AfterReturning: from method : " + method);

		// display the data returned
		myLogger.info(">>>> result : " + result);
	}

}
