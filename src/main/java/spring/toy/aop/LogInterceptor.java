package spring.toy.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LogInterceptor implements MethodInterceptor {

    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("log before");

        Object ret = invocation.proceed();

        System.out.println("log after");

        return ret;

    }
}
