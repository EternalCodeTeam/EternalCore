package com.eternalcode.core.loader;

import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class EternalCoreWrapper {

    private static final String LOADER_CORE_CLASS = "com.eternalcode.core.EternalCore";

    private final Class<?> eternalCoreClass;
    private Object eternalCore;

    EternalCoreWrapper(Class<?> eternalCoreClass) {
        this.eternalCoreClass = eternalCoreClass;
    }

    public void enable(Plugin plugin) {
        try {
            Constructor<?> eternalCoreConstructor = this.eternalCoreClass.getConstructor(Plugin.class);
            eternalCoreConstructor.setAccessible(true);

            this.eternalCore = eternalCoreConstructor.newInstance(plugin);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void disable() {
        try {
            Method disableMethod = this.eternalCoreClass.getMethod("disable");

            disableMethod.setAccessible(true);
            disableMethod.invoke(this.eternalCore);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static EternalCoreWrapper create(ClassLoader loader) {
        try {
            Class<?> eternalCoreClass = Class.forName(LOADER_CORE_CLASS, true, loader);

            return new EternalCoreWrapper(eternalCoreClass);
        }
        catch (ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

}
