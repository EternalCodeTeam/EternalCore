package com.eternalcode.core.injector.scan;

import com.eternalcode.core.injector.DependencyInjector;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.bean.BeanCandidate;
import com.eternalcode.core.injector.bean.BeanHolder;

import java.lang.reflect.Method;

class MethodBeanCandidate implements BeanCandidate {

    private final DependencyInjector dependencyInjector;
    private final Class<?> componentClass;
    private final Method method;
    private final Bean bean;

    public MethodBeanCandidate(DependencyInjector dependencyInjector, Class<?> componentClass, Method method, Bean bean) {
        this.dependencyInjector = dependencyInjector;
        this.componentClass = componentClass;
        this.method = method;
        this.bean = bean;
    }

    @Override
    public boolean isCandidate(Class<?> clazz) {
        Class<?> returnType = this.method.getReturnType();

        return clazz.isAssignableFrom(returnType);
    }

    @Override
    public Class<?> getType() {
        return this.method.getReturnType();
    }

    @Override
    public <T> BeanHolder<T> createBean(Class<T> clazz) {
        if (!this.isCandidate(clazz)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not a candidate for " + this.method.getReturnType().getName());
        }

        Object instanceOfComponent = this.dependencyInjector.getDependencyProvider().getDependency(this.componentClass);
        Object instanceOfBean = this.dependencyInjector.invokeMethod(instanceOfComponent, this.method);

        return BeanHolder.of(this.bean.value(), clazz.cast(instanceOfBean));
    }

    @Override
    public String toString() {
        return "MethodBeanCandidate{" +
            "componentClass=" + this.componentClass +
            ", method=" + this.method +
            '}';
    }
}
