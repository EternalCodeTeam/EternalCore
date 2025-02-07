package com.eternalcode.core.injector.bean;

import java.util.function.Supplier;

public class LazyBeanCandidate implements BeanCandidate {

    private final String name;
    private final Supplier<Object> instanceSupplier;
    private Object instance;

    public LazyBeanCandidate(String name, Supplier<Object> instanceSupplier) {
        this.name = name;
        this.instanceSupplier = instanceSupplier;
    }

    @Override
    public boolean isCandidate(Class<?> clazz) {
        return clazz.isAssignableFrom(getType());
    }

    @Override
    public Class<?> getType() {
        return this.getInstance().getClass();
    }

    @Override
    public <T> BeanHolder<T> createBean(Class<T> clazz) {
        if (!this.isCandidate(clazz)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not a candidate for " + this.getInstance().getClass().getName());
        }

        return BeanHolder.of(name, clazz.cast(this.getInstance()));
    }

    private Object getInstance() {
        if (this.instance == null) {
            this.instance = this.instanceSupplier.get();
        }

        return this.instance;
    }

    @Override
    public String toString() {
        return "LazyBeanCandidate{" +
            "instance=" + this.instance +
            '}';
    }
}
