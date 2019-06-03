package main.java.spring.simple.ioc;

public class SimpleIOCTest {

    public static void main(String[] args) throws Exception {
        String location = SimpleIOC.class.getClassLoader().getResource("ioc.xml").getFile();
        SimpleIOC ioc = new SimpleIOC(location);

        Car car = (Car) ioc.getBean("car");

        System.out.println(car);

    }
}
