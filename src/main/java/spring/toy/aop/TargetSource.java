package spring.toy.aop;

public class TargetSource {

    private final Class<?> targetClass;

    private final Class<?>[] interfaces;

    private final Object target;

    public TargetSource(Class<?> targetClass, Class<?>[] interfaces, Object target) {
        this.targetClass = targetClass;
        this.interfaces = interfaces;
        this.target = target;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Class<?>[] getInterfaces() {
        return interfaces;
    }

    public Object getTarget() {
        return target;
    }
}
