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

        // repositories
        this.libraryManager.addMavenCentral();
        this.libraryManager.addRepository("https://repo.panda-lang.org/releases/");
        this.libraryManager.addRepository("https://hub.spigotmc.org/nexus/content/repositories/snapshots/");
        this.libraryManager.addRepository("https://papermc.io/repo/repository/maven-public/");
        this.libraryManager.addRepository("https://repository.minecodes.pl/releases/");
        this.libraryManager.addRepository("https://repository.minecodes.pl/snapshots/");
    }

    public void registerDependency(Dependency dependency) {
        this.dependencies.add(dependency);
    }

    public void loadDependencies() {
        for (Dependency dependency : this.dependencies) {
            this.libraryManager.loadLibrary(dependency.createLibrary());
        }
    }
}
