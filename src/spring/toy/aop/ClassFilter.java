package spring.toy.aop;

public interface ClassFilter {

    Boolean matchers(Class beanClass) throws Exception;
}
