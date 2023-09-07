package com.eternalcode.core.dependencyyy.bean;

import com.eternalcode.core.dependencyyy.DependencyInjector;

class ComponentBeanCandidateImpl implements BeanCandidate {

    private final DependencyInjector dependencyInjector;
    private final Class<?> componentClass;

    public ComponentBeanCandidateImpl(DependencyInjector dependencyInjector, Class<?> componentClass) {
        this.dependencyInjector = dependencyInjector;
        this.componentClass = componentClass;
    }

    @Override
    public boolean isCandidate(Class<?> clazz) {
        return clazz.isAssignableFrom(this.componentClass);
    }

    @Override
    public <T> BeanHolder<T> createBean(Class<T> clazz) {
        Class<T> typed = this.toTypedCandidate(clazz);

        return BeanHolder.of(dependencyInjector.newInstance(typed));
    }

    @SuppressWarnings("unchecked")
    private <T> Class<T> toTypedCandidate(Class<T> clazz) {
        if (!isCandidate(clazz)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not a candidate for " + this.componentClass.getName());
        }

        return (Class<T>) this.componentClass;
    }

}
