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
        libraryManager.addRepository("https://repo.panda-lang.org/releases");
        libraryManager.addRepository("https://hub.spigotmc.org/nexus/content/repositories/snapshots/");
        libraryManager.addRepository("https://papermc.io/repo/repository/maven-public/");

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

        libraryManager.loadLibrary(expressible);
        libraryManager.loadLibrary(paperLib);
        libraryManager.loadLibrary(triumphGui);
        libraryManager.loadLibrary(ormLite);
        libraryManager.loadLibrary(postgreSql);
        libraryManager.loadLibrary(h2Database);
        libraryManager.loadLibrary(hikariCp);
        libraryManager.loadLibrary(mariaDb);
    }
}
