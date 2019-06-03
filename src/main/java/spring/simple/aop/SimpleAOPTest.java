package main.java.spring.simple.aop;

import spring.simple.aop.BeforeAdvice;
import spring.simple.aop.HelloService;

public class SimpleAOPTest {

    public static void main(String[] args) {


        HelloService helloService = new HelloServiceImpl();

        Advice advice = new BeforeAdvice(helloService, new MethodInvocation() {
            @Override
            public void invoke() {
                System.out.println("before hello");
            }
        });

        HelloService proxy = (HelloService) SimpleAOP.getProxy(helloService, advice);
        proxy.sayHello();
    }
}
