package com.eternalcode.core.injector.bean;

import java.lang.annotation.Annotation;
import java.util.List;

public interface BeanHolder<T> {

    String DEFAULT_NAME = "";

    String getName();

    Class<T> getType();

    List<Annotation> getAnnotations();

    T get();

    static <T> BeanHolder<T> of(T instance) {
        return new BeanHolderImpl<>(DEFAULT_NAME, instance);
    }

    static <T> BeanHolder<T> of(String name, T instance) {
        return new BeanHolderImpl<>(name, instance);
    }

}
