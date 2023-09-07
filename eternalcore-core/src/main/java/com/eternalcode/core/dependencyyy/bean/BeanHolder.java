package com.eternalcode.core.dependencyyy.bean;

public interface BeanHolder<T> {

    T get();

    Class<T> getType();

    static <T> BeanHolder<T> of(T instance) {
        return new BeanHolderImpl<>(instance);
    }

}
