package com.eternalcode.core.injector.scan;

import com.eternalcode.core.injector.DependencyInjector;
import com.eternalcode.core.injector.bean.BeanCandidate;
import com.eternalcode.core.injector.bean.BeanHolder;

import java.lang.annotation.Annotation;
import java.util.List;

class ComponentBeanCandidateImpl<COMPONENT extends Annotation> implements BeanCandidate {

    private final DependencyInjector dependencyInjector;
    private final Class<?> componentClass;
    private final COMPONENT component;
    private final ComponentNameProvider<COMPONENT> componentNameProvider;

    public ComponentBeanCandidateImpl(DependencyInjector dependencyInjector, Class<?> componentClass, COMPONENT component, ComponentNameProvider<COMPONENT> componentNameProvider) {
        this.dependencyInjector = dependencyInjector;
        this.componentClass = componentClass;
        this.component = component;
        this.componentNameProvider = componentNameProvider;
    }

    @Override
    public boolean isCandidate(Class<?> clazz) {
        return clazz.isAssignableFrom(this.componentClass);
    }

    @Override
    public <T> BeanHolder<T> createBean(Class<T> clazz) {
        Class<T> typed = this.toTypedCandidate(clazz);

        return new ComponentBeanHolderImpl<>(this.dependencyInjector.newInstance(typed));
    }

    @SuppressWarnings("unchecked")
    private <T> Class<T> toTypedCandidate(Class<T> clazz) {
        if (!this.isCandidate(clazz)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not a candidate for " + this.componentClass.getName());
        }

        return (Class<T>) this.componentClass;
    }

    private class ComponentBeanHolderImpl<T> implements BeanHolder<T> {

        private final T instance;

        ComponentBeanHolderImpl(T instance) {
            this.instance = instance;
        }

        @Override
        public T get() {
            return this.instance;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Class<T> getType() {
            return (Class<T>) this.instance.getClass();
        }

        @Override
        public String getName() {
            return ComponentBeanCandidateImpl.this.componentNameProvider.getName(ComponentBeanCandidateImpl.this.component);
        }

        @Override
        public List<Annotation> getAnnotations() {
            return List.of(ComponentBeanCandidateImpl.this.componentClass.getAnnotations());
        }

    }

    @Override
    public String toString() {
        return "ComponentBeanCandidateImpl{" +
            "componentClass=" + this.componentClass +
            ", component=" + this.component.annotationType()
            + '}';
    }

}
