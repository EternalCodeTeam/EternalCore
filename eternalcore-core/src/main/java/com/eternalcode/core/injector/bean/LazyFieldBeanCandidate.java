package com.eternalcode.core.injector.bean;

import io.sentry.Sentry;

import java.lang.reflect.Field;

public class LazyFieldBeanCandidate extends LazyBeanCandidate {

    private final Object instance;
    private final Field field;

    public LazyFieldBeanCandidate(Object instance, Field field) {
        super(() -> {
            try {
                field.setAccessible(true);

                return field.get(instance);
            }
            catch (IllegalAccessException exception) {
                Sentry.captureException(exception);
                throw new BeanException("Cannot access field " + field.getName() + " of " + instance.getClass().getName(), exception, field.getType());
            }
        });
        this.field = field;
        this.instance = instance;
    }

    @Override
    public String toString() {
        return "LazyFieldBeanCandidate{" +
            "instanceType=" + this.instance.getClass() +
            ", field=" + this.field +
            '}';
    }
}
