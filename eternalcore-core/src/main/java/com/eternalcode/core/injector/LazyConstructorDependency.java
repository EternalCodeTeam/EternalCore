package com.eternalcode.core.injector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;


class LazyConstructorDependency<T> extends LazyDependency<Constructor<?>, T> {

    LazyConstructorDependency(Class<T> typeToScan, Constructor<?> constructor) {
        super(typeToScan, constructor);
    }

    @Override
    public List<Annotation> getAnnotations() {
        return Arrays.stream(this.type.getAnnotations())
            .toList();
    }

    @Override
    Object newInstance(DependencyProvider dependencyProvider, Constructor<?> executable, Object[] arguments) throws InstantiationException, InvocationTargetException, IllegalAccessException {
        return executable.newInstance(arguments);
    }

}
