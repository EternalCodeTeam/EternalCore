package com.eternalcode.core.injector;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.bean.BeanException;
import io.sentry.Sentry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DependencyInjector {

    private final DependencyProvider dependencyProvider;

    public DependencyInjector(DependencyProvider dependencyProvider) {
        this.dependencyProvider = dependencyProvider;
    }

    public DependencyProvider getDependencyProvider() {
        return this.dependencyProvider;
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

    public Object invokeMethod(Object instance, Method method, Object... additionalDependencies) {
        Class<?> declaringClass = method.getDeclaringClass();
        Object[] parameters = new Object[method.getParameterCount()];

        int parameterIndex = 0;

        try {
            for (Class<?> parameterType : method.getParameterTypes()) {
                Object dependency = this.getDependency(parameterType, additionalDependencies);

                parameters[parameterIndex++] = dependency;
            }

            method.setAccessible(true);
            return method.invoke(instance, parameters);
        }
        catch (BeanException beanException) {
            Sentry.captureException(beanException);
            throw new DependencyInjectorException("Failed to invoke method " + method.getName() + " in " + declaringClass.getName() + "!", beanException, declaringClass);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
            Sentry.captureException(exception);
            throw new DependencyInjectorException(exception, declaringClass);
        }
    }

    private Object getDependency(Class<?> parameterType, Object... additionalDependencies) {
        for (Object additionalDependency : additionalDependencies) {
            if (parameterType.isInstance(additionalDependency)) {
                return additionalDependency;
            }
        }

        return this.dependencyProvider.getDependency(parameterType);
    }

    private boolean isInjectable(Constructor<?> constructor) {
        return constructor.getParameterCount() == 0 || constructor.isAnnotationPresent(Inject.class);
    }

    private <T> T newInstance(Constructor<T> constructor, Object... additionalDependencies) {
        Class<T> declaringClass = constructor.getDeclaringClass();
        Object[] parameters = new Object[constructor.getParameterCount()];

        int parameterIndex = 0;

        try {
            for (Class<?> parameterType : constructor.getParameterTypes()) {
                Object dependency = this.getDependency(parameterType, additionalDependencies);

                parameters[parameterIndex++] = dependency;
            }

            constructor.setAccessible(true);
            return constructor.newInstance(parameters);
        }
        catch (BeanException beanException) {
            Sentry.captureException(beanException);
            throw new DependencyInjectorException("Failed to create a new instance of " + declaringClass.getName() + "!", beanException, declaringClass);
        }
        catch (InvocationTargetException | InstantiationException | IllegalAccessException exception) {
            Sentry.captureException(exception);
            throw new DependencyInjectorException(exception, declaringClass);
        }
    }

}
