package com.eternalcode.core.loader;

import com.eternalcode.core.loader.classloader.IsolatedClassLoader;
import com.eternalcode.core.loader.dependency.DependencyLoadResult;
import com.eternalcode.core.loader.dependency.DependencyLoader;
import com.eternalcode.core.loader.dependency.DependencyLoaderImpl;
import org.bukkit.plugin.java.JavaPlugin;
import java.net.URL;
import java.net.URLClassLoader;

public class EternalCoreLoader extends JavaPlugin {

    private DependencyLoader dependencyLoader;
    private EternalCoreWrapper eternalCore;

    @Override
    public void onEnable() {
        URLClassLoader pluginLoader = (URLClassLoader) this.getClassLoader().getParent();
        URL pluginJarUrl = this.getClass().getProtectionDomain().getCodeSource().getLocation();

        this.dependencyLoader = new DependencyLoaderImpl(this.getDataFolder(), EternalCoreLoaderConstants.repositories());
        DependencyLoadResult result = this.dependencyLoader.load(
                pluginLoader,
                EternalCoreLoaderConstants.dependencies(),
                EternalCoreLoaderConstants.relocations(),
                pluginJarUrl
        );

        IsolatedClassLoader loader = result.loader();

        this.eternalCore = EternalCoreWrapper.create(loader);
        this.eternalCore.enable(this);
    }

    @Override
    public void onDisable() {
        this.eternalCore.disable();
        this.dependencyLoader.close();
    }

}
