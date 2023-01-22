package com.eternalcode.core.dependency;

import net.byteflux.libby.relocation.Relocation;

import java.util.List;
import java.util.stream.Stream;

public class DependencyConstants {

    private static final String PREFIX = "com.eternalcode.core.libs.";

    private static final List<Relocation> RELOCATION_LIST = Stream.of(
        "panda{}std",
        "panda{}utilities",
        "org{}panda_lang",
        "org{}bstats",
        "net{}dzikoysk",
        "dev{}rollczi{}litecommands",
        "dev{}rollczi{}liteskullapi",
        "net{}kyori",
        "io{}papermc{}lib",
        "dev{}triumphteam",
        "com{}google{}gson",
        "com{}eternalcode.containers",
        "net{}byteflux"
    ).map(DependencyConstants::relocation).toList();

    private static final Dependency ORMLITE = new Dependency("com{}j256{}ormlite", "ormlite-jdbc", "{ormlite_version}", RELOCATION_LIST);
    private static final Dependency PAPER_LIB = new Dependency("io{}papermc", "paperlib", "{paper_lib_version}", RELOCATION_LIST);
    private static final Dependency EXPRESSIBLE = new Dependency("org{}panda-lang", "expressible", "{expressible_version}", RELOCATION_LIST);
    private static final Dependency TRIUMPH_GUI = new Dependency("dev{}triumphteam", "triumph-gui", "{triumph_gui_version}", RELOCATION_LIST);
    private static final Dependency POSTGRESQL = new Dependency("org{}postgresql", "postgresql", "{postgresql_version}", RELOCATION_LIST);
    private static final Dependency H2_DATABASE = new Dependency("com{}h2database", "h2", "{h2_version}", RELOCATION_LIST);
    private static final Dependency HIKARI_CP = new Dependency("com{}zaxxer", "HikariCP", "{hikari_cp_version}", RELOCATION_LIST);
    private static final Dependency MARIADB = new Dependency("org{}mariadb{}jdbc", "mariadb-java-client", "{mariadb_version}", RELOCATION_LIST);
    private static final Dependency LITE_SKULL_API = new Dependency("dev{}rollczi", "liteskullapi", "{liteskullapi_version}", RELOCATION_LIST);
    private static final Dependency GSON = new Dependency("com{}google{}code", "gson", "2.10.1", RELOCATION_LIST);

    public static final Dependency[] DEPENDENCIES = {
        ORMLITE,
        PAPER_LIB,
        EXPRESSIBLE,
        POSTGRESQL,
        H2_DATABASE,
        HIKARI_CP,
        MARIADB,
        LITE_SKULL_API
    };

    public DependencyConstants() {}

    private static Relocation relocation(String relocationSource) {
        return new Relocation(relocationSource, PREFIX + relocationSource);
    }
}

