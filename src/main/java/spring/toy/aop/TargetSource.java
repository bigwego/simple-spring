package main.java.spring.toy.aop;

class TargetSource {

    private final Class<?> targetClass;

    private final Class<?>[] interfaces;

    private final Object target;

    TargetSource(Class<?> targetClass, Class<?>[] interfaces, Object target) {
        this.targetClass = targetClass;
        this.interfaces = interfaces;
        this.target = target;
    }

    Class<?> getTargetClass() {
        return targetClass;
    }

    Class<?>[] getInterfaces() {
        return interfaces;
    }

    Object getTarget() {
        return target;
    }
}
