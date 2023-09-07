package com.eternalcode.core.dependencyyy;

import com.eternalcode.core.dependencyyy.bean.BeanFactory;
import com.eternalcode.core.dependencyyy.bean.BeanHolder;
import com.eternalcode.core.injector.annotations.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DependencyInjector {

    private final BeanFactory beanFactory;

    public DependencyInjector(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @SuppressWarnings("unchecked")
    public <T> T newInstance(Class<T> clazz) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (this.isInjectable(constructor)) {
                return this.newInstance((Constructor<T>) constructor);
            }
        }

        throw new DependencyInjectorException("No injectable constructor found for " + clazz.getName() + "! Please, annotate one of the constructors with @Inject.", clazz);
    }

    public Object invokeMethod(Object instance, Method method) {
        Object[] parameters = new Object[method.getParameterCount()];

        int parameterIndex = 0;
        for (Class<?> parameterType : method.getParameterTypes()) {
            BeanHolder<?> bean = this.beanFactory.getSingletonBean(parameterType);

            parameters[parameterIndex++] = bean.get();
        }

        try {
            return method.invoke(instance, parameters);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
            throw new DependencyInjectorException(exception, method.getDeclaringClass());
        }
    }

    private boolean isInjectable(Constructor<?> constructor) {
        return constructor.getParameterCount() == 0 || constructor.isAnnotationPresent(Inject.class);
    }

    private <T> T newInstance(Constructor<T> constructor) {
        constructor.setAccessible(true);

        Object[] parameters = new Object[constructor.getParameterCount()];

        int parameterIndex = 0;
        for (Class<?> parameterType : constructor.getParameterTypes()) {
            BeanHolder<?> bean = this.beanFactory.getSingletonBean(parameterType);

            parameters[parameterIndex++] = bean.get();
        }

        try {
            return constructor.newInstance(parameters);
        }
        catch (InvocationTargetException | InstantiationException | IllegalAccessException exception) {
            throw new DependencyInjectorException(exception, constructor.getDeclaringClass());
        }
    }

}
