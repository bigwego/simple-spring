package main.java.spring.simple.aop;

import java.lang.reflect.Proxy;

class SimpleAOP {

    static Object getProxy(Object bean, Advice advice) {
        return Proxy.newProxyInstance(SimpleAOP.class.getClassLoader(),
                bean.getClass().getInterfaces(), advice);
    }
}
