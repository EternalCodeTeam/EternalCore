package com.eternalcode.core.loader;

import com.eternalcode.core.loader.dependency.DependencyManager;
import com.eternalcode.core.loader.dependency.DependencyRegistry;
import com.eternalcode.core.loader.dependency.classloader.IsolatedClassLoader;
import com.eternalcode.core.loader.dependency.classpath.ReflectionClassPathAppender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.function.Supplier;

public class EternalCodePluginLoader extends JavaPlugin {

    private static final String LOADER_CORE_CLASS = "com.eternalcode.core.EternalCore";

    private Class<?> eternalCoreClass;
    private Object eternalCore;

    public EternalCodePluginLoader() {
        URL urlOfPlugin = this.getClass().getProtectionDomain().getCodeSource().getLocation();
        IsolatedClassLoader loader = new IsolatedClassLoader(this.getClassLoader().getParent(), urlOfPlugin);

        DependencyManager dependencyManager = new DependencyManager(
                this.getDataFolder(),
                new ReflectionClassPathAppender(loader)
        );

        dependencyManager.loadDependencies(DependencyRegistry.getDependencies(), DependencyRegistry.getRelocations());

        try {
            this.eternalCoreClass = Class.forName(LOADER_CORE_CLASS, true, loader);
        }
        catch (ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void onEnable() {
        try {
            Constructor<?> eternalCoreConstructor = eternalCoreClass.getConstructor(Plugin.class);
            eternalCoreConstructor.setAccessible(true);

            this.eternalCore = eternalCoreConstructor.newInstance(this);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void onDisable() {
        try {
            this.eternalCoreClass.getMethod("disable").invoke(this.eternalCore);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            throw new RuntimeException(exception);
        }
    }

}
