package spring.simple.aop;

import java.lang.reflect.Method;

public class BeforeAdvice implements Advice {

    private final Object bean;

    private final MethodInvocation methodInvocation;

    BeforeAdvice(Object bean, MethodInvocation methodInvocation) {
        this.bean = bean;
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        methodInvocation.invoke();
        return method.invoke(bean, args);
    }
}
