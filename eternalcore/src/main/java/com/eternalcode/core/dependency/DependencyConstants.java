package com.eternalcode.core.dependency;

public class DependencyConstants {

    private static final Dependency ORMLITE = new Dependency("com{}j256{}ormlite", "ormlite-jdbc", "{ormlite_version}");
    private static final Dependency PAPER_LIB = new Dependency("io{}papermc", "paperlib", "{paper_lib_version}");
    private static final Dependency EXPRESSIBLE = new Dependency("org{}panda-lang", "expressible", "{expressible_version}");
    private static final Dependency TRIUMPH_GUI = new Dependency("dev{}triumphteam", "triumph-gui", "{triumph_gui_version}");
    private static final Dependency POSTGRESQL = new Dependency("org{}postgresql", "postgresql", "{postgresql_version}");
    private static final Dependency H2_DATABASE = new Dependency("com{}h2database", "h2", "{h2_version}");
    private static final Dependency HIKARI_CP = new Dependency("com{}zaxxer", "HikariCP", "{hikari_cp_version}");
    private static final Dependency MARIADB = new Dependency("org{}mariadb{}jdbc", "mariadb-java-client", "{mariadb_version}");
    private static final Dependency LITE_SKULL_API = new Dependency("dev{}rollczi", "liteskullapi", "{liteskullapi_version}");

    public static final Dependency[] DEPENDENCIES = { ORMLITE, PAPER_LIB, EXPRESSIBLE, TRIUMPH_GUI, POSTGRESQL, H2_DATABASE, HIKARI_CP, MARIADB, LITE_SKULL_API };

    public DependencyConstants() {}

}

