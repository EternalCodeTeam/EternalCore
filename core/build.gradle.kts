import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("eternalcode.java-conventions")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
    id("xyz.jpenilla.run-paper") version "2.0.1"
}

dependencies {
    // modules
    implementation(project(":api"))
    implementation(project(":paper"))

    // minecraft development api
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    implementation("io.papermc:paperlib:1.0.8")
    implementation("net.kyori:adventure-platform-bukkit:4.3.0")
    implementation("net.kyori:adventure-text-minimessage:4.13.1")

    // configuration
    implementation("net.dzikoysk:cdn:1.14.4")
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    // database
    compileOnly("com.j256.ormlite:ormlite-jdbc:6.1")
    compileOnly("com.zaxxer:HikariCP:5.0.1")

    // command framework
    implementation("dev.rollczi.litecommands:bukkit-adventure:2.8.7")

    // skull api
    implementation("dev.rollczi:liteskullapi:1.3.0")

    // utility library
    implementation("org.panda-lang:expressible:1.3.1")

    // gui library
    implementation("dev.triumphteam:triumph-gui:3.1.4")

    // metrics
    implementation("org.bstats:bstats-bukkit:3.0.2")

    // bridge (hook)
    compileOnly("me.clip:placeholderapi:2.11.3")

    // commons
    implementation("commons-io:commons-io:2.11.0")

    // unit test
    testImplementation("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.17")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    testImplementation("org.mockito:mockito-core:5.2.0")
    testImplementation("net.kyori:adventure-platform-bukkit:4.3.0")
    testImplementation("net.kyori:adventure-text-minimessage:4.13.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs = listOf("-Xlint:deprecation", "-parameters")
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
    libraries = listOf(
        "org.postgresql:postgresql:42.6.0",
        "com.h2database:h2:2.1.214",
        "com.j256.ormlite:ormlite-jdbc:6.1",
        "com.zaxxer:HikariCP:5.0.1",
        "org.mariadb.jdbc:mariadb-java-client:3.1.3"
    )
}

tasks {
    runServer {
        minecraftVersion("1.19.3")
    }
}

tasks.withType<ShadowJar> {
    archiveFileName.set("EternalCore v${project.version} (MC 1.17-1.19x).jar")

    dependsOn("checkstyleMain")
    dependsOn("checkstyleTest")
    dependsOn("test")

    exclude(
        "org/intellij/lang/annotations/**",
        "org/jetbrains/annotations/**",
        "org/checkerframework/**",
        "META-INF/**",
        "javax/**"
    )

    mergeServiceFiles()
    minimize()

    val prefix = "com.eternalcode.core.libs"
    listOf(
        "panda",
        "org.panda_lang",
        "org.bstats",
        "net.dzikoysk",
        "dev.rollczi",
        "net.kyori",
        "io.papermc.lib",
        "dev.triumphteam",
        "org.slf4j",
        "com.google.gson",
        "com.eternalcode.containers",
        "commons-io",
    ).forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }
}

description = "core"
