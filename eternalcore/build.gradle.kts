import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("xyz.jpenilla.run-paper") version "2.0.1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.kyori.blossom") version "1.2.0"
}

val adventurePlatformVersion = "4.2.0"
val minimessageVersion = "4.12.0"
val cdnVersion = "1.14.2"
val lombokVersion = "1.18.24"
val litecommandsVersion = "2.7.0"
val bstatsVersion = "3.0.0"
val spigotApiVersion = "1.19.3-R0.1-SNAPSHOT"
val paperlibVersion = "1.0.8"
val liteskullapiVersion = "1.3.0"
val expressibleVersion = "1.2.2"
val ormliteJdbcVersion = "6.1"
val hikariCPVersion = "5.0.1"
val triumphGuiVersion = "3.1.4"
val placeholderapiVersion = "2.11.2"

// tests
val mockBukkitVersion = "2.144.3"
val groovyAllVersion = "3.0.14"
val junitJupiterApiVersion = "5.9.2"
val junitJupiterParamsVersion = "5.9.2"

// drivers
val postgresqlVersion = "42.3.1"
val h2Database = "1.4.200"
val mariaDbVersion = "2.7.3"

dependencies {
    // modules
    implementation(project(":eternalcore-paper"))

    // adventure & minimessages
    implementation("net.kyori:adventure-platform-bukkit:$adventurePlatformVersion")
    implementation("net.kyori:adventure-text-minimessage:$minimessageVersion")

    // configuration
    compileOnly("net.dzikoysk:cdn:$cdnVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // command framework
    implementation("dev.rollczi.litecommands:bukkit-adventure:$litecommandsVersion")

    // metrics
    implementation("org.bstats:bstats-bukkit:$bstatsVersion")

    // dependency management (libby)
    implementation("net.byteflux:libby-bukkit:1.1.6")
    implementation("net.byteflux:libby-core:1.1.6")

    // minecraft development api
    compileOnly("org.spigotmc:spigot-api:$spigotApiVersion")
    compileOnly("io.papermc:paperlib:$paperlibVersion")

    // skull api
    compileOnly("dev.rollczi:liteskullapi:$liteskullapiVersion")

    // utility library
    compileOnly("org.panda-lang:expressible:$expressibleVersion")

    // database
    compileOnly("com.j256.ormlite:ormlite-jdbc:$ormliteJdbcVersion")
    compileOnly("com.zaxxer:HikariCP:$hikariCPVersion")

    // gui library
    compileOnly("dev.triumphteam:triumph-gui:$triumphGuiVersion")

    // bridge (hook)
    compileOnly("me.clip:placeholderapi:$placeholderapiVersion")

    // unit test
    testImplementation("org.spigotmc:spigot-api:$spigotApiVersion")
    testImplementation("org.codehaus.groovy:groovy-all:$groovyAllVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterApiVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterParamsVersion")
    testImplementation("com.github.seeseemelk:MockBukkit-v1.19:$mockBukkitVersion")
    testImplementation("net.kyori:adventure-platform-bukkit:$adventurePlatformVersion")
    testImplementation("net.kyori:adventure-text-minimessage:$minimessageVersion")
    testImplementation("net.dzikoysk:cdn:$cdnVersion")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs = listOf("-Xlint:deprecation")
    options.encoding = "UTF-8"
}

bukkit {
    main = "com.eternalcode.core.EternalCore"
    apiVersion = "1.13"
    prefix = "EternalCore"
    author = "EternalCodeTeam"
    name = "EternalCore"
    description = "All the most important server functions in one!"
    website = "www.eternalcode.pl"
    version = "${project.version}"
    softDepend = listOf("PlaceholderAPI")
}


tasks {
    runServer {
        minecraftVersion("1.19.3")
    }
}

blossom {
    val dependencyConstants = "src/main/java/com/eternalcode/core/dependency/DependencyConstants.java"

    replaceToken("{ormlite_version}", ormliteJdbcVersion, dependencyConstants)
    replaceToken("{paper_lib_version}", paperlibVersion, dependencyConstants)
    replaceToken("{expressible_version}", expressibleVersion, dependencyConstants)
    replaceToken("{triumph_gui_version}", triumphGuiVersion, dependencyConstants)
    replaceToken("{postgresql_version}", postgresqlVersion, dependencyConstants)
    replaceToken("{h2_version}", h2Database, dependencyConstants)
    replaceToken("{hikari_cp_version}", hikariCPVersion, dependencyConstants)
    replaceToken("{mariadb_version}", mariaDbVersion, dependencyConstants)
    replaceToken("{liteskullapi_version}", liteskullapiVersion, dependencyConstants)
}

tasks.withType<ShadowJar> {
    archiveFileName.set("EternalCore v${project.version} (MC 1.17-1.19x).jar")

    exclude(
        "org/intellij/lang/annotations/**",
        "org/jetbrains/annotations/**",
        "org/checkerframework/**",
        "META-INF/**",
        "javax/**",
    )

    mergeServiceFiles()
    minimize()

    val prefix = "com.eternalcode.core.libs"
    listOf(
        "panda",
        "net.kyori",
        "net.dzikoysk",
        "dev.rollczi.litecommands",
        "org.bstats",
        "com.eternalcode.containers",
        "net.byteflux"
    ).forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }
}

