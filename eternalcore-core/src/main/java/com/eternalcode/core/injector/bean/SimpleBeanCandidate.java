package com.eternalcode.core.injector.bean;

import java.util.function.Supplier;

class SimpleBeanCandidate implements BeanCandidate {

    private final String name;
    private final Class<?> type;
    private final Supplier<?> instance;

    public SimpleBeanCandidate(String name, Class<?> type, Supplier<?> instance) {
        this.name = name;
        this.type = type;
        this.instance = instance;
    }

    @Override
    public boolean isCandidate(Class<?> clazz) {
        return clazz.isAssignableFrom(this.type);
    }

    @Override
    public <T> BeanHolder<T> createBean(Class<T> clazz) {
        if (!this.isCandidate(clazz)) {
            throw new IllegalArgumentException("Cannot create bean of type " + clazz.getName() + " from " + this.type.getName() + "!");
        }

        return BeanHolder.of(this.name, clazz.cast(this.instance.get()));
    }

    @Override
    public String toString() {
        return "SimpleBeanCandidate{" +
            "name='" + this.name + '\'' +
            ", type=" + this.type +
            '}';
    }

}
