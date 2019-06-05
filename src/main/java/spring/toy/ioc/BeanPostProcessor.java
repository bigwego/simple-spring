package spring.toy.ioc;

public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String name);

    Object postProcessAfterInitialization(Object bean, String name) throws Exception;
}
