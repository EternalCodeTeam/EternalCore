package com.eternalcode.core;

import com.eternalcode.core.dependency.DependencyRegistry;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class EternalCoreBootstrap extends JavaPlugin {

    private EternalCore eternalCore;

    public EternalCoreBootstrap() {}

    protected EternalCoreBootstrap(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        DependencyRegistry dependencyRegistry = new DependencyRegistry();
        dependencyRegistry.loadLibraries(this);

        // bStats metrics
        new Metrics(this, 13964);
        //metrics.addCustomChart(new SingleLineChart("users", () -> 0));

        this.eternalCore = new EternalCore(this);
        this.eternalCore.enable();
    }

    @Override
    public void onDisable() {
        this.eternalCore.disable();
    }
}
