package main.java.spring.toy.aop;

public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();
}
