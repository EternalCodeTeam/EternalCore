package com.eternalcode.core.loader.dependency;

public class DependencyException extends RuntimeException {

    public DependencyException() {
    }

    public DependencyException(String message) {
        super(message);
    }

    public DependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public DependencyException(Throwable cause) {
        super(cause);
    }

}
