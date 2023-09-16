package com.eternalcode.core.injector.bean;

import java.util.function.Supplier;

public class LazyBeanCandidate implements BeanCandidate {

    private final Supplier<Object> instanceSupplier;
    private Object instance;

    public LazyBeanCandidate(Supplier<Object> instanceSupplier) {
        this.instanceSupplier = instanceSupplier;
    }

    @Override
    public boolean isCandidate(Class<?> clazz) {
        Class<?> type = this.getInstance().getClass();

        return clazz.isAssignableFrom(type);
    }

    @Override
    public <T> BeanHolder<T> createBean(Class<T> clazz) {
        if (!this.isCandidate(clazz)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not a candidate for " + this.getInstance().getClass().getName());
        }

        return BeanHolder.of(clazz.cast(this.getInstance()));
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
