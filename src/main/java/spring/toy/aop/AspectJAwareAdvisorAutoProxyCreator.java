package main.java.spring.toy.aop;

import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;

import com.teamlab.hellospringbootthymeleaf.spring.toy.ioc.BeanPostProcessor;
import com.teamlab.hellospringbootthymeleaf.spring.toy.ioc.factory.BeanFactory;
import com.teamlab.hellospringbootthymeleaf.spring.toy.ioc.factory.BeanFactoryAware;
import com.teamlab.hellospringbootthymeleaf.spring.toy.ioc.xml.XmlBeanFactory;

public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {

    private XmlBeanFactory xmlBeanFactory;

    public Object postProcessBeforeInitialization(Object bean, String name) {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String name) throws Exception {
        if (bean instanceof AspectJExpressionPointcutAdvisor) {
            return bean;
        }

        if (bean instanceof MethodInterceptor) {
            return bean;
        }

        List<AspectJExpressionPointcutAdvisor> advisors =
                xmlBeanFactory.getBeansForType(AspectJExpressionPointcutAdvisor.class);

        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            if (advisor.getPointcut().getClassFilter().matchers(bean.getClass())) {
                ProxyFactory advisedSupport = new ProxyFactory();
                advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdivce());
                advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

                TargetSource targetSource = new TargetSource(bean.getClass(),
                        bean.getClass().getInterfaces(), bean);
                advisedSupport.setTargetSource(targetSource);

                return advisedSupport.getProxy();
            }
        }

        return bean;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws Exception {
        xmlBeanFactory = (XmlBeanFactory) beanFactory;
    }
}
