package com.eternalcode.core.injector;

@FunctionalInterface
public interface DependencyProcessor<T> {

    void process(DependencyProvider dependencyProvider, T instance);

}
