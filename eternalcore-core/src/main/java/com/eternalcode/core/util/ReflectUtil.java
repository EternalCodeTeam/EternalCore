package com.eternalcode.core.util;

import com.google.common.base.Preconditions;
import com.google.common.reflect.ClassPath;
import io.sentry.Sentry;

import java.io.IOException;
import java.util.ArrayList;
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
            Sentry.captureException(exception);
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

}
