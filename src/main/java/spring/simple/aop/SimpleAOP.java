package spring.simple.aop;

import java.lang.reflect.Proxy;

public class SimpleAOP {

    static Object getProxy(Object bean, Advice advice) {
        return Proxy.newProxyInstance(SimpleAOP.class.getClassLoader(),
                bean.getClass().getInterfaces(), advice);
    }
}
