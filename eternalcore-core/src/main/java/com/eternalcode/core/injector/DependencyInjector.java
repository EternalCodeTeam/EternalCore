package com.eternalcode.core.injector;

import com.eternalcode.core.util.MapUtil;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class DependencyInjector implements DependencyProvider {

    private final Map<Class<?>, Dependency<?>> dependencies = new HashMap<>();
    private final DependencyProcessors processors;

    public DependencyInjector() {
        this.processors = new DependencyProcessors();
    }

    public DependencyInjector(DependencyProcessors processors) {
        this.processors = processors;
    }

    public DependencyInjector scan(UnaryOperator<DependencyScanner> operator) {
        DependencyScanner dependencyScanner = operator.apply(new DependencyScanner(this.processors));

        for (LazyDependency<?, ?> dependency : dependencyScanner.scan()) {
            this.dependencies.put(dependency.getType(), dependency);
        }

        return this;
    }

    public DependencyInjector registerSelf() {
        return this.register(DependencyInjector.class, () ->  this);
    }

    public <T> DependencyInjector register(Class<T> type, Dependency<T> dependency) {
        this.dependencies.put(type, dependency);
        return this;
    }

    public <T> DependencyInjector register(Class<T> type, Supplier<T> supplier) {
        this.register(type, dependencyProvider -> supplier.get());
        return this;
    }

    public <T> DependencyInjector register(Class<T> type) {
        for (LazyDependency<?, ?> dependency : LazyDependency.scanClassForDependencies(type)) {
            this.register(dependency);
        }

        return this;
    }

    private <T> DependencyInjector register(LazyDependency<?, T> dependency) {
        return this.register(dependency.getType(), dependency);
    }

    @Override
    public <T> T getDependency(Class<T> type) {
        Dependency<T> dependency = this.findDependency(type);
        return dependency.get(this);
    }

    public Object invoke(@Nullable Object instance, Method method, Object... args) {
        Object[] newArgs = new Object[method.getParameterCount()];

        int i = 0;
        for (Parameter parameter : method.getParameters()) {
            Object arg = this.findFromArgs(args, parameter.getType());

            if (arg != null) {
                newArgs[i++] = arg;
                continue;
            }

            newArgs[i++] = this.getDependency(parameter.getType());
        }

        try {
            method.setAccessible(true);
            return method.invoke(instance, newArgs);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new InjectException("Cannot invoke method " + method.getName() + " in " + method.getDeclaringClass().getName(), e);
        }
    }

    private @Nullable Object findFromArgs(Object[] args, Class<?> type) {
        for (Object arg : args) {
            if (type.isAssignableFrom(arg.getClass())) {
                return arg;
            }
        }

        return null;
    }

    public DependencyInjector initializeAll() {
        for (Dependency<?> dependency : this.dependencies.values()) {
            this.processors.process(this, dependency);
        }

        return this;
    }

    @SuppressWarnings("unchecked")
    private <T> Dependency<T> findDependency(Class<T> type) {
        return (Dependency<T>) MapUtil.findByInstanceOf(type, this.dependencies)
            .orElseThrow(() -> new InjectDependencyNotFoundException("Cannot find dependency for " + type.getName(), type));
    }

}
