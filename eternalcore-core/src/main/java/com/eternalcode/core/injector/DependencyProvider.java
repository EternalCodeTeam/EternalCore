package com.eternalcode.core.injector;

public interface DependencyProvider {

    <T> T getDependency(Class<T> type);

}
