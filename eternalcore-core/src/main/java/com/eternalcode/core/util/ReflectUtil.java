package com.eternalcode.core.util;

import com.google.common.base.Preconditions;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public final class ReflectUtil {

    private static final Logger LOGGER = Logger.getLogger("EternalCore");

    public static final String LIBS_PACKAGE_PREFIX = "com.eternalcode.core.libs.";

    private ReflectUtil() {
    }

    public static List<Class<?>> scanClasses(String packageToScan, ClassLoader classLoader) {
        Preconditions.checkNotNull(packageToScan, "Package to scan cannot be null!");
        Preconditions.checkNotNull(classLoader, "Class loader cannot be null!");

        try {
            ClassPath classPath = ClassPath.from(classLoader);
            Set<ClassPath.ClassInfo> classes = classPath.getTopLevelClassesRecursive(packageToScan);
            List<Class<?>> loadedClasses = new ArrayList<>();

            for (ClassPath.ClassInfo info : classes) {
                if (info.getPackageName().startsWith(LIBS_PACKAGE_PREFIX)) {
                    continue;
                }

                try {
                    Class<?> clazz = Class.forName(info.getName(), false, classLoader);
                    loadedClasses.add(clazz);
                }
                catch (NoClassDefFoundError ignored) {
                    LOGGER.severe("Unable to load class: " + info.getName());
                }
            }

            return loadedClasses;
        }
        catch (IOException | ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Set<Class<?>> getAllSuperClasses(Class<?> base) {
        Set<Class<?>> classes = new LinkedHashSet<>();
        classes.add(base);
        for (Class<?> baseInterface : base.getInterfaces()) {
            classes.addAll(getAllSuperClasses(baseInterface));
        }

        Class<?> superclass = base.getSuperclass();

        if (superclass != null) {
            classes.addAll(getAllSuperClasses(superclass));
        }

        return classes;
    }

    @SuppressWarnings("unchecked")
    public static <T> T unsafeCast(Object object) {
        return (T) object;
    }

    public static List<Field> getAllSuperFields(Class<?> aClass) {
        List<Field> fields = new ArrayList<>();
        Class<?> currentClass = aClass;

        while (currentClass != null && currentClass != Object.class) {
            Collections.addAll(fields, currentClass.getDeclaredFields());

            currentClass = currentClass.getSuperclass();
        }

        return fields;
    }

    public static <T> T getFieldValue(Field declaredField, Object object) {
        try {
            declaredField.setAccessible(true);
            return unsafeCast(declaredField.get(object));
        }
        catch (IllegalAccessException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static Method getDeclaredMethod(Class<?> clazz, String methodName) {
        try {
            return clazz.getDeclaredMethod(methodName);
        }
        catch (NoSuchMethodException exception) {
            throw new IllegalArgumentException("Method " + methodName + " not found in class " + clazz.getName(), exception);
        }
    }

    public static Object invokeMethod(Method method, Object target, Object... args) {
        try {
            return method.invoke(target, args);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Failed to invoke method " + method.getName(), e);
        }
    }
}
