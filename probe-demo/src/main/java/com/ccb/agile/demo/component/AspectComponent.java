package com.ccb.agile.demo.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;

@Aspect
@Component
public class AspectComponent {

  @Autowired
  MeterRegistry meterRegistry;

  private Counter requestCounter;

  @PostConstruct
  public void init() {
    requestCounter = meterRegistry.counter("request_aop", "method", "AspectComponent");
  }

  @Pointcut("execution(* com.ccb.agile.demo.controller.MetricsController.*(..))")
  public void pointcut() {

  }

  @Before("pointcut()")
  public void before() {
    requestCounter.increment();
  }

  @After("pointcut()")
  public void after() {
    System.out.println("after commit");
  }

  @AfterReturning(value = "pointcut()", returning = "returnObject")
  public void afterReturning(JoinPoint joinPoint, Object returnObject) {
    System.out.println("afterReturning");
  }

  @AfterThrowing("pointcut()")
  public void afterThrowing() {
    System.out.println("afterThrowing rollback");
  }

  @Around("pointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    try {
      System.out.println("around");
      return joinPoint.proceed();
    } catch (Throwable e) {
      e.printStackTrace();
      throw e;
    } finally {
      System.out.println("around");
    }
  }
}
