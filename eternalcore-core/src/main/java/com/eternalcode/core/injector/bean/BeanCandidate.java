package com.eternalcode.core.injector.bean;

import java.util.function.Supplier;

public interface BeanCandidate {

    boolean isCandidate(Class<?> clazz);

    <T> BeanHolder<T> createBean(Class<T> clazz);

    static <T> BeanCandidate of(String name, Class<T> type, Supplier<T> instance) {
        return new SimpleBeanCandidate(name, type, instance);
    }

    static <T> BeanCandidate of(Class<T> type, Supplier<T> instance) {
        return new SimpleBeanCandidate(BeanHolder.DEFAULT_NAME, type, instance);
    }

}
