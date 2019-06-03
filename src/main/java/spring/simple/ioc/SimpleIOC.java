package main.java.spring.simple.ioc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class SimpleIOC {

    private final Map<String, Object> beanMap = new HashMap<>();

    SimpleIOC(String location) throws Exception {
        loadBeans(location);
    }

    Object getBean(String name) {
        Object bean = beanMap.get(name);
        if (bean == null) {
            throw new IllegalArgumentException("no such bean with name: " + name);
        }
        return bean;
    }

    private void loadBeans(String location) throws Exception {
        InputStream is = new FileInputStream(location);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(is);
        Element root = doc.getDocumentElement();
        NodeList nodes = root.getChildNodes();

        for (int i = 0, len = nodes.getLength(); i < len; i++) {
            Node node = nodes.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                String id = ele.getAttribute("id");
                String className = ele.getAttribute("class");

                Class beanClass = null;
                try {
                    beanClass = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return;
                }

                Object bean = beanClass.newInstance();

                NodeList propertyNodes = ele.getElementsByTagName("property");
                for (int j = 0; j < propertyNodes.getLength(); j++) {
                    Node propertyNode = propertyNodes.item(j);
                    if (propertyNode instanceof Element) {
                        Element propertyEle = (Element) propertyNode;
                        String name = propertyEle.getAttribute("name");
                        String value = propertyEle.getAttribute("value");

                        Field declaredField = bean.getClass().getDeclaredField(name);
                        declaredField.setAccessible(true);

                        if (value != null && value.length() > 0) {
                            declaredField.set(bean, value);
                        } else {
                            String ref = propertyEle.getAttribute("ref");
                            if (ref == null || ref.length() == 0) {
                                throw new IllegalArgumentException("ref config error");
                            }
                            declaredField.set(bean, getBean(ref));
                        }

                        registerBean(id, bean);
                    }
                }
            }
        }
    }

    private void registerBean(String id, Object bean) {
        beanMap.put(id, bean);
    }
}
