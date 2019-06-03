package main.java.spring.toy.ioc.xml;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.teamlab.hellospringbootthymeleaf.spring.toy.ioc.BeanDefinition;
import com.teamlab.hellospringbootthymeleaf.spring.toy.ioc.BeanPostProcessor;
import com.teamlab.hellospringbootthymeleaf.spring.toy.ioc.BeanReference;
import com.teamlab.hellospringbootthymeleaf.spring.toy.ioc.PropertyValue;
import com.teamlab.hellospringbootthymeleaf.spring.toy.ioc.factory.BeanFactory;
import com.teamlab.hellospringbootthymeleaf.spring.toy.ioc.factory.BeanFactoryAware;

public class XmlBeanFactory implements BeanFactory {

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();

    private final List<String> beanDefinitionNames = new ArrayList<>();

    private final XmlBeanDefinitionReader beanDefinitionReader;

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    public XmlBeanFactory(String location) throws Exception {
        beanDefinitionReader = new XmlBeanDefinitionReader();
        loadBeanDefinitions(location);
    }

    private Object getBean(String name) throws Exception {
        BeanDefinition bd = beanDefinitionMap.get(name);
        if (bd == null) {
            throw new IllegalArgumentException("no such bean with name: " + name);
        }

        Object bean = bd.getBean();
        if (bean == null) {
            bean = createBean(bd);
            bean = initializeBean(bean, name);
            bd.setBean(bean);
        }
        return bean;
    }

    private Object createBean(BeanDefinition bd) throws Exception {
        Object bean = bd.getBeanClass().newInstance();
        applyPropertyValues(bean, bd);
        return bean;
    }

    private void applyPropertyValues(Object bean, BeanDefinition bd) throws Exception {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }

        for (PropertyValue pv : bd.getPvs().getPropertyValues()) {
            Object value = pv.getValue();
            if (value instanceof BeanReference) {
                BeanReference br = (BeanReference) value;
                value = getBean(br.getName());
            }

            try {
                String setMethodName = "set" + pv.getName().substring(0, 1).toUpperCase() + pv.getName().substring(1);
                Method declaredMethod = bean.getClass().getDeclaredMethod(setMethodName, value.getClass());
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(bean, value);
            } catch (NoSuchMethodException e) {
                Field declaredField = bean.getClass().getDeclaredField(pv.getName());
                declaredField.setAccessible(true);
                declaredField.set(bean, value);
            }
        }
    }

    private Object initializeBean(Object bean, String name) throws Exception {
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
        }

        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
        }
        return bean;
    }

    private void loadBeanDefinitions(String location) throws Exception {
        beanDefinitionReader.loadBeanDefinitions(location);
        registerBeanDefinitions();
        registerBeanPostProcessors();
    }

    private void registerBeanDefinitions() {
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionReader.getRegistry().entrySet()) {
            beanDefinitionMap.put(entry.getKey(), entry.getValue());
            beanDefinitionNames.add(entry.getKey());
        }
    }

    private void registerBeanPostProcessors() throws Exception {
        List<Object> beans = getBeansForType(BeanPostProcessor.class);
        for (Object bean : beans) {
            addBeanPostProcessor((BeanPostProcessor) bean);
        }
    }

    private void addBeanPostProcessor(BeanPostProcessor bean) {
        beanPostProcessors.add(bean);
    }

    private List getBeansForType(Class type) throws Exception {
        List beans = new ArrayList();
        for (String bdName : beanDefinitionNames) {
            BeanDefinition bd = beanDefinitionMap.get(bdName);
            if (type.isAssignableFrom(bd.getBeanClass())) {
                beans.add(getBean(bdName));
            }
        }
        return beans;
    }
}
