package com.eternalcode.core.injector;

public class InjectIllegalDependencyException extends InjectException {

    private final Class<?> dependencyType;

    public InjectIllegalDependencyException(Class<?> dependencyType) {
        this.dependencyType = dependencyType;
    }

    public InjectIllegalDependencyException(String message, Class<?> dependencyType) {
        super(message);
        this.dependencyType = dependencyType;
    }

    public InjectIllegalDependencyException(String message, Throwable cause, Class<?> dependencyType) {
        super(message, cause);
        this.dependencyType = dependencyType;
    }

    public InjectIllegalDependencyException(Throwable cause, Class<?> dependencyType) {
        super(cause);
        this.dependencyType = dependencyType;
    }

    public Class<?> getDependencyType() {
        return this.dependencyType;
    }

}
