package com.eternalcode.core.dependency;

import net.byteflux.libby.BukkitLibraryManager;
import net.byteflux.libby.Library;
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

        // for testing, please remove after testing
        Library library = Library.builder()
            .groupId("dev{}rollczi{}litecommands")
            .artifactId("bukkit-adventure")
            .version("2.7.2")
            .classifier("all")
            .relocate("dev{}rollczi{}litecommands", "com{}eternalcode{}core{}libs{}dev{}rollczi{}litecommands")
            .build();

        Library adventurePlatform = Library.builder()
            .groupId("net{}kyori")
            .artifactId("adventure-platform-bukkit")
            .version("4.2.0")
            .relocate("net{}kyori", "com{}eternalcode{}core{}libs{}net{}kyori")
            .build();

        Library miniMessage = Library.builder()
            .groupId("net{}kyori")
            .artifactId("adventure-text-minimessage")
            .version("4.12.0")
            .relocate("net{}kyori", "com{}eternalcode{}core{}libs{}net{}kyori")
            .build();

        this.libraryManager.loadLibrary(adventurePlatform);
        this.libraryManager.loadLibrary(miniMessage);
        this.libraryManager.loadLibrary(library);
    }
}
