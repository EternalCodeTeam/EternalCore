package com.eternalcode.core.dependencyyy.bean;

class BeanHolderImpl<T> implements BeanHolder<T> {

    private final T instance;

    BeanHolderImpl(T instance) {
        this.instance = instance;
    }

    @Override
    public T get() {
        return this.instance;
    }

}
