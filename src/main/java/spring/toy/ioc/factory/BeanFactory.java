package spring.toy.ioc.factory;

public interface BeanFactory {

    Object getBean(String beanId) throws Exception;
}
