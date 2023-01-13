package com.eternalcode.core.dependency;

import org.bukkit.plugin.Plugin;

public class DependencyRegistry {

    private static final Dependency ORMLITE = new Dependency("com.j256.ormlite", "ormlite-jdbc", "6.1");
    private static final Dependency PAPER_LIB = new Dependency("io.papermc", "paperlib", "1.0.8");
    private static final Dependency EXPRESSIBLE = new Dependency("org.panda-lang", "expressible", "1.2.2");
    private static final Dependency TRIUMPH_GUI = new Dependency("dev.triumphteam", "triumph-gui", "3.1.4");
    private static final Dependency POSTGRESQL = new Dependency("org.postgresql", "postgresql", "42.5.1");
    private static final Dependency H2_DATABASE = new Dependency("com.h2database", "h2", "2.1.214");
    private static final Dependency HIKARI_CP = new Dependency("com.zaxxer", "HikariCP", "5.0.1");
    private static final Dependency MARIADB = new Dependency("org.mariadb.jdbc", "mariadb-java-client", "3.1.0");
    private static final Dependency LITE_SKULL_API = new Dependency("dev.rollczi", "liteskullapi", "1.3.0");

    private static final Dependency[] DEPENDENCIES = { ORMLITE, PAPER_LIB, EXPRESSIBLE, TRIUMPH_GUI, POSTGRESQL, H2_DATABASE, HIKARI_CP, MARIADB, LITE_SKULL_API };

    public void loadLibraries(Plugin plugin) {
        DependencyManager dependencyManager = new DependencyManager(plugin);

        for (Dependency dependency : DEPENDENCIES) {
            dependencyManager.registerDependency(dependency);
        }

        // load dependencies
        dependencyManager.loadDependencies();
    }
}

