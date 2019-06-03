package main.java.spring.toy.ioc.test;

import com.teamlab.hellospringbootthymeleaf.spring.toy.ioc.xml.XmlBeanFactory;

public class ToyIOCTest {

    public static void main(String[] args) throws Exception {
        XmlBeanFactory factory = new XmlBeanFactory(XmlBeanFactory.class.getClassLoader().getResource("ioc.xml").getFile());

        System.out.println(factory.getBean("car"));
    }
}
