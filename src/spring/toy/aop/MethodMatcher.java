package spring.toy.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {

    Boolean matchers(Method method, Class beanClass);
}
