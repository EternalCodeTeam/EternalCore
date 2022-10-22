import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("java-library")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    // lombok (Only for configs.)
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    // EternalCore paper features
    implementation(project(":eternalcore-paper"))

    // paper lib, spigot api
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
    implementation("io.papermc:paperlib:1.0.8-SNAPSHOT")

    // Kyori Adventure
    implementation("net.kyori:adventure-platform-bukkit:4.1.2")
    implementation("net.kyori:adventure-text-minimessage:4.11.0")

    // LiteCommands
    implementation("dev.rollczi.litecommands:bukkit-adventure:2.6.0")
    implementation("dev.rollczi:liteskullapi:1.3.0")

    // cdn configs
    implementation("net.dzikoysk:cdn:1.14.1")

    // expressible
    implementation("org.panda-lang:expressible:1.2.1")

    // bStats
    implementation("org.bstats:bstats-bukkit:3.0.0")

    // ormlite jdbc
    compileOnly("com.j256.ormlite:ormlite-jdbc:6.1")

    // hikari
    compileOnly("com.zaxxer:HikariCP:5.0.1")

    // TriumphGui
    implementation("dev.triumphteam:triumph-gui:3.1.3")

    // tests
    testImplementation("org.spigotmc:spigot:1.19-R0.1-SNAPSHOT")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.13")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
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
    version = "${project.version}"
    libraries = listOf(
        "org.postgresql:postgresql:42.5.0",
        "com.h2database:h2:2.1.214",
        "com.j256.ormlite:ormlite-jdbc:6.1",
        "com.zaxxer:HikariCP:5.0.1",
        "org.mariadb.jdbc:mariadb-java-client:3.0.8"
    )
}


tasks {
    runServer {
        minecraftVersion("1.19.2")
    }
}

tasks.withType<ShadowJar> {
    val timestamp = SimpleDateFormat("dd-MM-yyyy_hh-mm").format(Date())

    archiveFileName.set("EternalCore v${project.version} $timestamp (MC 1.17-1.19x).jar")

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
        "com.eternalcode.containers"
    ).forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }
}

