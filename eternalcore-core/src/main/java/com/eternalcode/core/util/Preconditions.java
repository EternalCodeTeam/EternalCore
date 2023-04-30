package com.eternalcode.core.util;

import java.util.Collection;
import java.util.Map;

public final class Preconditions {

    private Preconditions() {
    }

    public static void notNull(Object object, String name) {
        if (object == null) {
            throw new NullPointerException(name + " cannot be null");
        }
    }

    public static void isNull(Object object, String name) {
        if (object != null) {
            throw new IllegalArgumentException(name + " must be null");
        }
    }

    public static void isTrue(boolean expression, String name) {
        if (!expression) {
            throw new IllegalArgumentException(name + " must be true");
        }
    }

    public static void isFalse(boolean expression, String name) {
        if (expression) {
            throw new IllegalArgumentException(name + " must be false");
        }
    }

    public static void notEmpty(String string, String name) {
        if (string.isEmpty()) {
            throw new IllegalArgumentException(name + " cannot be empty");
        }
    }

    public static void notEmpty(Collection<?> collection, String name) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(name + " cannot be empty");
        }
    }

    public static void isMoreThan(Collection<?> collection, int size, String name) {
        if (collection.size() <= size) {
            throw new IllegalArgumentException(name + " must be more than " + size);
        }
    }

    public static void notEmpty(Map<?, ?> map, String name) {
        if (map.isEmpty()) {
            throw new IllegalArgumentException(name + " cannot be empty");
        }
    }

}