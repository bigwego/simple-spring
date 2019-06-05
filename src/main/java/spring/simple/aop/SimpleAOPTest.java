package spring.simple.aop;

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
