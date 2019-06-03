package spring.toy.aop.test;

public class IOCAOPTest {

    public static void main(String[] args) throws Exception {
//        XmlBeanFactory factory = new XmlBeanFactory(XmlBeanFactory.class.getClassLoader().getResource("ioc.xml").getFile());
////
////        //Object car = factory.getBean("car");
////
////        HelloService service = (HelloService) factory.getBean("helloService");
////
////        service.sayHello();

        IOCAOPTest.class.getDeclaredMethod("method", Super.class, Sub.class);
    }

    private void method(Super s) {

    }

//    public static void main(String[] args) throws NoSuchMethodException {
//        IOCAOPTest.class.getDeclaredMethod("method", String.class);
//    }
//
//    public void method(Object obj) {
//
//    }
}

class Super {

}

class Sub extends Super {

}
