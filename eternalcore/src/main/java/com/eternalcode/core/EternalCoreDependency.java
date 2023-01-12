package com.eternalcode.core;

import net.byteflux.libby.BukkitLibraryManager;
import net.byteflux.libby.Library;
import org.bukkit.plugin.Plugin;

public class EternalCoreDependency {

    public void loadLibraries(Plugin plugin) {
        BukkitLibraryManager libraryManager = new BukkitLibraryManager(plugin);

        // repositories
        libraryManager.addMavenCentral();

        // custom repositories
        libraryManager.addRepository("https://repository.minecodes.pl/releases");
        libraryManager.addRepository("https://repository.minecodes.pl/snapshots");
        libraryManager.addRepository("https://repo.panda-lang.org/releases");
        libraryManager.addRepository("https://hub.spigotmc.org/nexus/content/repositories/snapshots/");


        Library ormLite = Library.builder()
            // com.j256.ormlite:ormlite-jdbc:6.1
            .groupId("com.j256.ormlite")
            .artifactId("ormlite-jdbc")
            .version("6.1")
            .build();

        Library paperLib = Library.builder()
            // io.papermc:paperlib:1.0.8
            .groupId("io.papermc")
            .artifactId("paperlib")
            .version("1.0.8")
            .build();

        Library adventurePlatform = Library.builder()
            // net.kyori:adventure-platform-bukkit:4.2.0
            .groupId("net.kyori")
            .artifactId("adventure-platform-bukkit")
            .version("4.2.0")
            .build();

        Library miniMessages = Library.builder()
            // net.kyori:adventure-text-minimessage:4.12.0
            .groupId("net.kyori")
            .artifactId("adventure-text-minimessage")
            .version("4.2.0")
            .build();

        Library cdnConfigs = Library.builder()
            // net.dzikoysk:cdn:1.14.2
            .groupId("net.dzikoysk")
            .artifactId("cdn")
            .version("1.14.2")
            .build();

        Library liteCommands = Library.builder()
            // dev.rollczi.litecommands:bukkit-adventure:2.7.0
            .groupId("dev.rollczi.litecommands")
            .artifactId("bukkit-adventure")
            .version("2.7.0")
            .build();

        Library skullApi = Library.builder()
            // dev.rollczi:liteskullapi:1.3.0
            .groupId("dev.rollczi")
            .artifactId("liteskullapi")
            .version("1.3.0")
            .build();

        Library expressible = Library.builder()
            // org.panda-lang:expressible:1.2.2
            .groupId("org.panda-lang")
            .artifactId("expressible")
            .version("1.2.2")
            .build();

        Library triumphGui = Library.builder()
            // dev.triumphteam:triumph-gui:3.1.4
            .groupId("dev.triumphteam")
            .artifactId("triumph-gui")
            .version("3.1.4")
            .build();

        Library bStats = Library.builder()
            // org.bstats:bstats-bukkit:3.0.0
            .groupId("org.bstats")
            .artifactId("bstats-bukkit")
            .version("3.0.0")
            .build();

        Library postgreSql = Library.builder()
            // org.postgresql:postgresql:42.5.1
            .groupId("org.postgresql")
            .artifactId("postgresql")
            .version("42.5.1")
            .build();

        Library h2Database = Library.builder()
            // com.h2database:h2:2.1.214
            .groupId("com.h2database")
            .artifactId("h2")
            .version("2.1.214")
            .build();

        Library hikariCp = Library.builder()
            // com.zaxxer:HikariCP:5.0.1
            .groupId("com.zaxxer")
            .artifactId("HikariCP")
            .version("5.0.1")
            .build();

        Library mariaDb = Library.builder()
            // org.mariadb.jdbc:mariadb-java-client:3.1.0
            .groupId("org.mariadb.jdbc")
            .artifactId("mariadb-java-client")
            .version("3.1.0")
            .build();

/*
        libraryManager.loadLibrary(paperLib);
        libraryManager.loadLibrary(adventurePlatform);
        libraryManager.loadLibrary(miniMessages);
        libraryManager.loadLibrary(cdnConfigs);
        libraryManager.loadLibrary(liteCommands);
        libraryManager.loadLibrary(skullApi);
        libraryManager.loadLibrary(expressible);
        libraryManager.loadLibrary(triumphGui);
  */

        libraryManager.loadLibrary(bStats);
        libraryManager.loadLibrary(ormLite);
        libraryManager.loadLibrary(postgreSql);
        libraryManager.loadLibrary(h2Database);
        libraryManager.loadLibrary(hikariCp);
        libraryManager.loadLibrary(mariaDb);
    }
}
