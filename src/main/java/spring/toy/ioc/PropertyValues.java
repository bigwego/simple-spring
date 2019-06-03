package main.java.spring.toy.ioc;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private final List<PropertyValue> propertyValues = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv) {
        propertyValues.add(pv);
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }
}
