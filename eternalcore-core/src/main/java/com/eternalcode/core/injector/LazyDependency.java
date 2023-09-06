package com.eternalcode.core.injector;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.library.AdventureSetup;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract class LazyDependency<E extends Executable, T> implements Dependency<T> {

    private static final ThreadLocal<List<Class<?>>> dependencyStack = ThreadLocal.withInitial(ArrayList::new);

    protected final Class<T> type;

    private final E executable;
    private T instance;

    private boolean locked = false;

    protected LazyDependency(Class<T> type, E executable) {
        this.type = type;
        this.executable = executable;
    }

    public Class<T> getType() {
        return this.type;
    }

    @NotNull
    @Override
    public T get(DependencyProvider dependencyProvider) {
        if (this.locked) {
            ArrayList<Class<?>> copyOfStack = new ArrayList<>(dependencyStack.get());
            copyOfStack.add(this.type);
            copyOfStack.add(copyOfStack.get(0));

            String schemeOfCycledDependency = String.join(" -> ", copyOfStack.stream()
                .map(type -> type.getName())
                .toArray(value -> new String[value]));

            throw new InjectException("Circular dependency detected for " + this.type.getName() + " in " + schemeOfCycledDependency);
        }

        this.locked = true;

        try {
            if (this.instance == null) {
                dependencyStack.get().add(this.type);
                this.instance = this.createInstance(this.executable, dependencyProvider);
                dependencyStack.get().remove(this.type);
            }
        }
        catch (InjectDependencyNotFoundException exception) {
            throw new InjectException("Cannot create instance of " + this.type.getName() + " because of missing dependency " + exception.getDependencyType().getName());
        }
        finally {
            this.locked = false;
        }

        return this.instance;
    }

    @SuppressWarnings("unchecked")
    private T createInstance(E executable, DependencyProvider dependencyProvider) {
        executable.setAccessible(true);

        Parameter[] parameters = executable.getParameters();
        Object[] arguments = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            arguments[i] = dependencyProvider.getDependency(parameters[i].getType());
        }

        try {
            return (T) this.newInstance(dependencyProvider, executable, arguments);
        }
        catch (InstantiationException | InvocationTargetException | IllegalArgumentException | IllegalAccessException exception) {
            throw new InjectException(exception);
        }
    }

    abstract Object newInstance(DependencyProvider dependencyProvider, E executable, Object[] arguments) throws InstantiationException, InvocationTargetException, IllegalAccessException;

    static List<LazyDependency<?, ?>> scanClassForDependencies(Class<?> typeToScan) {
        List<LazyDependency<?, ?>> dependencies = new ArrayList<>();

        if (typeToScan == AdventureSetup.class) {
            System.out.println("test");
        }

        for (Constructor<?> constructor : typeToScan.getDeclaredConstructors()) {
            createConstructorDependency(typeToScan, constructor)
                .ifPresent(dependency -> dependencies.add(dependency));
        }

        for (Method method : typeToScan.getDeclaredMethods()) {
            createMethodDependency(method.getReturnType(), method)
                .ifPresent(dependency -> dependencies.add(dependency));
        }

        return dependencies;
    }

    private static  <T> Optional<LazyMethodDependency<T>> createMethodDependency(Class<T> returnType, Method method) {
        if (!isInjectableMethod(method)) {
            return Optional.empty();
        }

        return Optional.of(new LazyMethodDependency<>(returnType, method));
    }

    private static <T> Optional<LazyConstructorDependency<T>> createConstructorDependency(Class<T> typeToScan, Constructor<?> constructor) {
        if (!isInjectableConstructor(constructor)) {
            return Optional.empty();
        }

        return Optional.of(new LazyConstructorDependency<>(typeToScan, constructor));
    }

    private static boolean isInjectableConstructor(Constructor<?> constructor) {
        return constructor.getParameterCount() == 0 || constructor.isAnnotationPresent(Inject.class);
    }

    private static boolean isInjectableMethod(Method method) {
        return method.getReturnType() != Void.class && method.isAnnotationPresent(Bean.class);
    }

}
