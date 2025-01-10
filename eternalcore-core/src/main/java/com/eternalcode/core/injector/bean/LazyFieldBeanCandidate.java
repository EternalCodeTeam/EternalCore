package com.eternalcode.core.injector.bean;

import com.eternalcode.core.injector.DependencyInjector;
import com.eternalcode.core.injector.annotations.Bean;
import java.lang.reflect.Field;
import java.util.function.Supplier;

public class LazyFieldBeanCandidate extends LazyBeanCandidate {

    private final Supplier<Object> instance;
    private final Field field;
    private final Class<?> instanceType;

    public LazyFieldBeanCandidate(Supplier<Object> instance, Field field, Bean bean) {
        super(bean.value(), () -> {
            try {
                field.setAccessible(true);

                return field.get(instance.get());
            }
            catch (IllegalAccessException exception) {
                throw new BeanException("Cannot access field " + field.getName() + " of " + instance.getClass().getName(), exception, field.getType());
            }
        });
        this.field = field;
        this.instance = instance;
        this.instanceType = field.getType();
    }

    public LazyFieldBeanCandidate(DependencyInjector dependencyInjector, Class<?> componentClass, Field field, Bean bean) {
        this(() -> dependencyInjector.newInstance(componentClass), field, bean);
    }

    @Override
    public Class<?> getType() {
        return this.instanceType;
    }

    @Override
    public String toString() {
        return "LazyFieldBeanCandidate{" +
            "instanceType=" + this.instance.getClass() +
            ", field=" + this.field +
            '}';
    }
}
