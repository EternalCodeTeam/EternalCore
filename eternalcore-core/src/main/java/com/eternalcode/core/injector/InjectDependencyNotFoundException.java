package com.eternalcode.core.injector;

public class InjectDependencyNotFoundException extends InjectException {

    private final Class<?> dependencyType;

    public InjectDependencyNotFoundException(Class<?> dependencyType) {
        this.dependencyType = dependencyType;
    }

    public InjectDependencyNotFoundException(String message, Class<?> dependencyType) {
        super(message);
        this.dependencyType = dependencyType;
    }

    public InjectDependencyNotFoundException(String message, Throwable cause, Class<?> dependencyType) {
        super(message, cause);
        this.dependencyType = dependencyType;
    }

    public InjectDependencyNotFoundException(Throwable cause, Class<?> dependencyType) {
        super(cause);
        this.dependencyType = dependencyType;
    }

    public Class<?> getDependencyType() {
        return this.dependencyType;
    }

}
