package com.eternalcode.core.dependency;

import org.bukkit.plugin.Plugin;

public class DependencyRegistry {

    public void loadLibraries(Plugin plugin) {
        DependencyManager dependencyManager = new DependencyManager(plugin);

        for (Dependency dependency : DependencyConstants.DEPENDENCIES) {
            dependencyManager.registerDependency(dependency);
        }

        // load dependencies
        dependencyManager.loadDependencies();
    }
}

