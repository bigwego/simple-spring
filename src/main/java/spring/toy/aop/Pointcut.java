package spring.toy.aop;

import spring.toy.aop.ClassFilter;
import spring.toy.aop.MethodMatcher;

public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
