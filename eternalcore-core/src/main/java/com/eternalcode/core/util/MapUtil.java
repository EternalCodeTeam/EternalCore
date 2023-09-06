package com.eternalcode.core.util;

import panda.std.Option;

import java.util.Map;
import java.util.Optional;

public final class MapUtil {

    private MapUtil() {}

    public static <E> Optional<E> findByInstanceOf(Class<?> type, Map<Class<?>, E> map) {
        E element = map.get(type);

        if (element != null) {
            return Optional.of(element);
        }

        for (Map.Entry<Class<?>, E> entry : map.entrySet()) {
            if (type.isAssignableFrom(entry.getKey())) {
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }

    public static <E> Optional<E> findBySuperTypeOf(Class<?> type, Map<Class<?>, E> map) {
        Optional<E> elementBySuperClass = findKeySuperTypeOf0(type, map);

        if (elementBySuperClass.isPresent()) {
            return elementBySuperClass;
        }

        for (Class<?> anInterface : type.getInterfaces()) {
            E elementByInterface = map.get(anInterface);

            if (elementByInterface != null) {
                return Optional.of(elementByInterface);
            }
        }

        E elementByObject = map.get(Object.class);

        if (elementByObject != null) {
            return Optional.of(elementByObject);
        }

        return Optional.empty();
    }

    private static <E> Optional<E> findKeySuperTypeOf0(Class<?> type, Map<Class<?>, E> map) {
        E element = map.get(type);

        if (element != null) {
            return Optional.of(element);
        }

        Class<?> superclass = type.getSuperclass();

        if (superclass != null && superclass != Object.class) {
            Optional<E> option = findBySuperTypeOf(superclass, map);

            if (option.isPresent()) {
                return option;
            }
        }

        return Optional.empty();
    }



}
