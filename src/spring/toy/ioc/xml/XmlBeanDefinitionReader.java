package spring.toy.ioc.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.teamlab.hellospringbootthymeleaf.spring.toy.ioc.BeanDefinition;
import com.teamlab.hellospringbootthymeleaf.spring.toy.ioc.BeanDefinitionReader;
import com.teamlab.hellospringbootthymeleaf.spring.toy.ioc.BeanReference;
import com.teamlab.hellospringbootthymeleaf.spring.toy.ioc.PropertyValue;

class XmlBeanDefinitionReader implements BeanDefinitionReader {

    private final Map<String, BeanDefinition> registry;

    XmlBeanDefinitionReader() {
        registry = new HashMap<String, BeanDefinition>();
    }

    void loadBeanDefinitions(String location) throws FileNotFoundException, Exception {
        InputStream is = new FileInputStream(location);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(is);
        Element root = doc.getDocumentElement();
        parseBeanDefinitions(root);
    }

    private void parseBeanDefinitions(Element root) throws Exception {
        NodeList nodes = root.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                parseBeanDefinition(ele);
            }
        }
    }

    private void parseBeanDefinition(Element ele) throws ClassNotFoundException {
        String name = ele.getAttribute("id");
        String className = ele.getAttribute("class");
        BeanDefinition bd = new BeanDefinition();
        bd.setBeanClassName(className);
        bd.setBeanClass(Class.forName(className));
        processProperty(bd, ele);
        registry.put(name, bd);
    }

    private void processProperty(BeanDefinition bd, Element ele) {
        NodeList propertyNodes = ele.getElementsByTagName("property");

        for (int i = 0; i < propertyNodes.getLength(); i++) {
            Node node = propertyNodes.item(i);
            if (node instanceof Element) {
                Element propertyEle = (Element) node;
                String name = propertyEle.getAttribute("name");
                String value = propertyEle.getAttribute("value");
                if (value != null && value.length() > 0) {
                    bd.getPvs().addPropertyValue(new PropertyValue(name, value));
                } else {
                    String ref = propertyEle.getAttribute("ref");
                    if (ref == null || ref.length() == 0) {
                        throw new IllegalArgumentException("ref config error");
                    }
                    BeanReference br = new BeanReference(ref);
                    bd.getPvs().addPropertyValue(new PropertyValue(name, br));
                }
            }
        }
    }

    Map<String, BeanDefinition> getRegistry() {
        return registry;
    }
}
