package spring.toy.aop;

abstract class AbstractAopProxy implements AopProxy {

    AdvisedSupport advisedSupport;

    AbstractAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }
}
