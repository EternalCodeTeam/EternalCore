package com.eternalcode.core.injector;

import java.lang.annotation.Annotation;
import java.util.List;

interface Dependency<T> {

    T get(DependencyProvider dependencyProvider);

    default List<Annotation> getAnnotations() {
        return List.of();
    }

}
