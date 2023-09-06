package com.eternalcode.core.util;

import com.eternalcode.core.util.ReflectUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReflectUtilTest {

    @Test
    @DisplayName("should return list of classes when valid package name and class loader are provided")
    void testScanClassesWhenValidPackageNameAndClassLoaderThenReturnListOfClasses() {
        String packageName = "com.eternalcode.core.util";

        List<Class<?>> classes = ReflectUtil.scanClasses(packageName, classLoader());

        assertFalse(classes.isEmpty(), "Expected non-empty list of classes");
        assertTrue(classes.stream().allMatch(clazz -> clazz.getPackage().getName().startsWith(packageName)),
            "Expected at least one class from the package " + packageName);
    }

    @Test
    @DisplayName("should return empty list of classes when invalid package name is provided")
    void testScanClassesWhenInvalidPackageNameThenThrowRuntimeException() {
        List<Class<?>> classes = ReflectUtil.scanClasses("com.invalid.package", classLoader());

        assertEquals(0, classes.size());
    }

    @Test
    @DisplayName("should throw NullPointerException when null package name is provided")
    void testScanClassesWhenNullPackageNameThenThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> ReflectUtil.scanClasses(null, classLoader()),
            "Expected NullPointerException to be thrown for null package name");
    }

    @Test
    void testScanClassesWhenNullClassLoaderThenThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> ReflectUtil.scanClasses("com.eternalcode.core.reflect", null),
            "Expected NullPointerException to be thrown for null class loader");
    }

    private ClassLoader classLoader() {
        return this.getClass().getClassLoader();
    }

}
