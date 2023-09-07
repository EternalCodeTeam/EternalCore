package com.eternalcode.core.injector.bean;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

class BeanHolderImpl<T> implements BeanHolder<T> {

    private final String name;
    private final T instance;

    BeanHolderImpl(String name, T instance) {
        this.name = name;
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
    public List<Annotation> getAnnotations() {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return this.name;
    }

}
