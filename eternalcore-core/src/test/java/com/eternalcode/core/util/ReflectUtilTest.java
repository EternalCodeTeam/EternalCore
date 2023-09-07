package com.eternalcode.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReflectUtilTest {

    static class MockSuperClass {
    }

    static class MockSubClass extends MockSuperClass implements MockInterface {
    }

    interface MockInterface {
    }

    interface MockSubInterface extends MockInterface {
    }

    @Test
    void testGetAllSuperClassesWhenClassHasSuperClassesThenReturnAll() {
        // Arrange
        Class<?> inputClass = MockSubClass.class;

        // Act
        var result = ReflectUtil.getAllSuperClasses(inputClass);

        // Assert
        Assertions.assertEquals(4, result.size());
        Assertions.assertTrue(result.contains(MockSubClass.class));
        Assertions.assertTrue(result.contains(MockSuperClass.class));
        Assertions.assertTrue(result.contains(MockInterface.class));
        Assertions.assertTrue(result.contains(Object.class));
    }

    @Test
    void testGetAllSuperClassesWhenClassHasNoSuperClassesThenReturnSelfAndObject() {
        // Arrange
        Class<?> inputClass = MockSuperClass.class;

        // Act
        var result = ReflectUtil.getAllSuperClasses(inputClass);

        // Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(MockSuperClass.class));
        Assertions.assertTrue(result.contains(Object.class));
    }

    @Test
    void testGetAllSuperClassesWhenNullInputThenThrowException() {
        // Arrange
        Class<?> inputClass = null;

        // Act & Assert
        Assertions.assertThrows(NullPointerException.class, () -> ReflectUtil.getAllSuperClasses(inputClass));
    }

    @Test
    void testGetAllInterfacesFromInterface() {
        // Arrange
        Class<?> inputClass = MockSubInterface.class;

        // Act
        var result = ReflectUtil.getAllSuperClasses(inputClass);

        // Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(MockSubInterface.class));
        Assertions.assertTrue(result.contains(MockInterface.class));
    }

}