package com.eternalcode.core.loader;

import com.eternalcode.core.loader.classloader.IsolatedClassAccessorLoader;
import com.eternalcode.core.loader.dependency.DependencyLoader;
import com.eternalcode.core.loader.dependency.DependencyLoaderImpl;
import org.bukkit.plugin.java.JavaPlugin;
import java.net.URLClassLoader;

public class EternalCoreLoader extends JavaPlugin {

    private DependencyLoader dependencyLoader;
    private EternalCoreWrapper eternalCore;

    @Override
    public void onEnable() {
        URLClassLoader pluginLoader = (URLClassLoader) this.getClassLoader();

        this.dependencyLoader = new DependencyLoaderImpl(this.getLogger(), this.getDataFolder(), EternalCoreLoaderConstants.repositories());
        this.dependencyLoader.load(
                new IsolatedClassAccessorLoader(pluginLoader),
                EternalCoreLoaderConstants.dependencies(),
                EternalCoreLoaderConstants.relocations()
        );

        this.eternalCore = EternalCoreWrapper.create(pluginLoader);
        this.eternalCore.enable(this);
    }

    @Override
    public void onDisable() {
        this.eternalCore.disable();
        this.dependencyLoader.close();
    }

}
