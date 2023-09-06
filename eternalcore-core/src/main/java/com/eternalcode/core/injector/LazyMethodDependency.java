package com.eternalcode.core.injector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class LazyMethodDependency<T> extends LazyDependency<Method, T> {
    LazyMethodDependency(Class<T> returnType, Method executable) {
        super(returnType, executable);
    }

    @Override
    Object newInstance(DependencyProvider dependencyProvider, Method executable, Object[] arguments) throws InvocationTargetException, IllegalAccessException {
        if (Modifier.isStatic(executable.getModifiers())) {
            return executable.invoke(null, arguments);
        }

        return executable.invoke(dependencyProvider.getDependency(executable.getDeclaringClass()), arguments);
    }
}
