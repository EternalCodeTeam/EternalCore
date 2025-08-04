package com.eternalcode.core.injector.bean;

import com.eternalcode.core.injector.DependencyInjector;
import com.eternalcode.core.injector.annotations.Bean;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.function.Supplier;

public class LazyFieldBeanCandidate extends LazyBeanCandidate {

    private final Field field;
    private final Class<?> resolvedType;

    public LazyFieldBeanCandidate(Supplier<Object> instance, Field field, Bean bean) {
        super(bean.value(), createSupplier(instance, field, bean));
        this.field = field;
        this.resolvedType = resolveType(field, bean);
    }

    public LazyFieldBeanCandidate(DependencyInjector dependencyInjector, Class<?> componentClass, Field field, Bean bean) {
        this(() -> dependencyInjector.getDependencyProvider().getDependency(componentClass), field, bean);
    }

    private static Supplier<Object> createSupplier(Supplier<Object> instance, Field field, Bean bean) {
        Class<?> type = resolveType(field, bean);
        field.setAccessible(true);

        if (bean.proxied().equals(Class.class)) {
            return () -> extractValue(instance, field, type);
        }

        return () -> Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, createInvocationHandler(instance, field, type));
    }

    private static InvocationHandler createInvocationHandler(Supplier<Object> instance, Field field, Class<?> type) {
        return (proxyInstance, method, args) -> {
            try {
                Object instanceValue = instance.get();
                Object fieldValue = field.get(instanceValue);
                return method.invoke(fieldValue, args);
            } catch (IllegalAccessException exception) {
                throw new BeanException("Cannot access field " + field.getName() + " of " + field.getType().getName(), exception, type);
            } catch (InvocationTargetException exception) {
                throw exception.getTargetException();
            }
        };
    }

    private static Object extractValue(Supplier<Object> instance, Field field, Class<?> type) {
        try {
            Object instanceValue = instance.get();
            return field.get(instanceValue);
        } catch (IllegalAccessException exception) {
            throw new BeanException("Cannot access field " + field.getName() + " of " + type.getName(), exception, type);
        }
    }

    @Override
    public Class<?> getType() {
        return this.resolvedType;
    }

    private static Class<?> resolveType(Field field, Bean bean) {
        Class<?> proxy = bean.proxied();
        Class<?> type = field.getType();
        if (proxy.equals(Class.class)) {
            return type;
        }

        if (!proxy.isInterface()) {
            throw new BeanException("You can not proxy filed " + field.getName() + " of " + type.getName() + " to " + proxy.getName() + " because it is not an interface!", proxy);
        }

        if (!proxy.isAssignableFrom(type)) {
            throw new BeanException("You can not proxy filed " + field.getName() + " because " + proxy.getName() + " is not assignable from " + type.getName() + "!", proxy);
        }

        return proxy;
    }

    @Override
    public String toString() {
        return "LazyFieldBeanCandidate{" +
            "instanceType=" + resolvedType.getName() +
            ", field=" + field.getName() +
            '}';
    }
}