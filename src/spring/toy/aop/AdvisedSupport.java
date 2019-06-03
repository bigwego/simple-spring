package spring.toy.aop;

import org.aopalliance.intercept.MethodInterceptor;

class AdvisedSupport {

    private TargetSource targetSource;

    private MethodInterceptor methodInterceptor;

    private MethodMatcher methodMatcher;

    TargetSource getTargetSource() {
        return targetSource;
    }

    void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
