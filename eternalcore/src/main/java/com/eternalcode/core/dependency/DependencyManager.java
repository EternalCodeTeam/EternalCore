package com.eternalcode.core.dependency;

import net.byteflux.libby.BukkitLibraryManager;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class DependencyManager {
    private final List<Dependency> dependencies = new ArrayList<>();
    private final BukkitLibraryManager libraryManager;

    public DependencyManager(Plugin plugin) {
        this.libraryManager = new BukkitLibraryManager(plugin);
    }

    public void registerDependency(Dependency dependency) {
        dependencies.add(dependency);
    }

    public void loadDependencies() {
        for (Dependency dependency : dependencies) {
            libraryManager.loadLibrary(dependency.createLibrary());
        }
    }
}
