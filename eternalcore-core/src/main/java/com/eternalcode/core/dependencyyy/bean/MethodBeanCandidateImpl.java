package com.eternalcode.core.dependencyyy.bean;

import com.eternalcode.core.dependencyyy.DependencyInjector;

import java.lang.reflect.Method;

class MethodBeanCandidateImpl implements BeanCandidate {

    private final DependencyInjector dependencyInjector;
    private final Object instance;
    private final Method method;

    public MethodBeanCandidateImpl(DependencyInjector dependencyInjector, Object instance, Method method) {
        this.dependencyInjector = dependencyInjector;
        this.instance = instance;
        this.method = method;
    }

    @Override
    public boolean isCandidate(Class<?> clazz) {
        Class<?> returnType = method.getReturnType();

        return clazz.isAssignableFrom(returnType);
    }

    @Override
    public <T> BeanHolder<T> createBean(Class<T> clazz) {
        Object bean = dependencyInjector.invokeMethod(instance, method);

        return () -> clazz.cast(bean);
    }

}
