package main.java.spring.toy.aop.test;

import java.lang.reflect.Method;

import com.teamlab.hellospringbootthymeleaf.spring.simple.aop.HelloService;
import com.teamlab.hellospringbootthymeleaf.spring.simple.aop.HelloServiceImpl;
import com.teamlab.hellospringbootthymeleaf.spring.toy.aop.AdvisedSupport;
import com.teamlab.hellospringbootthymeleaf.spring.toy.aop.JdkDynamicAopProxy;
import com.teamlab.hellospringbootthymeleaf.spring.toy.aop.LogInterceptor;
import com.teamlab.hellospringbootthymeleaf.spring.toy.aop.MethodMatcher;
import com.teamlab.hellospringbootthymeleaf.spring.toy.aop.TargetSource;

public class AOPTest {

    public static void main(String[] args) {
        System.out.println("========= NO PROXY ===========");
        HelloService service = new HelloServiceImpl();
        service.sayHello();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setMethodInterceptor(new LogInterceptor());

        TargetSource targetSource = new TargetSource(HelloServiceImpl.class,
                HelloServiceImpl.class.getInterfaces(), service);
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodMatcher(new MethodMatcher() {
            public Boolean matchers(Method method, Class beanClass) {
                return true;
            }
        });

        service = (HelloService) new JdkDynamicAopProxy(advisedSupport).getProxy();

        System.out.println("======== WITH PROXY ========");
        service.sayHello();

    }
}
