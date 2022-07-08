import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

dependencies {
    // lombok
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    // paper lib, spigot api
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
    api("io.papermc:paperlib:1.0.7")

    // Kyori Adventure
    implementation("net.kyori:adventure-platform-bukkit:4.1.1")
    implementation("net.kyori:adventure-text-minimessage:4.11.0")

    // LiteCommands
    implementation("dev.rollczi.litecommands:bukkit-adventure:2.3.4")
    implementation("dev.rollczi:liteskullapi:1.1.0")

    // cdn configs
    implementation("net.dzikoysk:cdn:1.13.23")

    // expressible
    implementation("org.panda-lang:expressible:1.1.20")

    // bStats
    implementation("org.bstats:bstats-bukkit:3.0.0")

    // ormlite jdbc
    compileOnly("com.j256.ormlite:ormlite-jdbc:6.1")

    // hikari
    compileOnly("com.zaxxer:HikariCP:5.0.1")

    // TriumphGui
    implementation("dev.triumphteam:triumph-gui:3.1.2")

    // tests
    testImplementation("org.spigotmc:spigot:1.19-R0.1-SNAPSHOT")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.11")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

bukkit {
    main = "com.eternalcode.core.EternalCore"
    apiVersion = "1.13"
    prefix = "EternalCore"
    author = "EternalCodeTeam"
    name = "EternalCore"
    description = "Essential plugin for your server!"
    version = "${project.version}"
    libraries = listOf(
        "org.postgresql:postgresql:42.4.0",
        "com.h2database:h2:2.1.214",
        "com.j256.ormlite:ormlite-jdbc:6.1",
        "com.zaxxer:HikariCP:5.0.1",
        "org.mariadb.jdbc:mariadb-java-client:3.0.6"
    )
}


tasks {
    runServer {
        minecraftVersion("1.18.2")
    }
}

tasks.withType<ShadowJar> {
    archiveFileName.set("EternalCore v${project.version} (MC 1.17-1.18x).jar")

    exclude("org/intellij/lang/annotations/**")
    exclude("org/jetbrains/annotations/**")
    exclude("org/checkerframework/**")
    exclude("META-INF/**")
    exclude("javax/**")

    mergeServiceFiles()
    minimize()

    relocate("panda", "com.eternalcode.core.libs.org.panda")
    relocate("org.panda_lang", "com.eternalcode.core.libs.org.panda")
    relocate("net.dzikoysk", "com.eternalcode.core.libs.net.dzikoysk")
    relocate("dev.rollczi", "com.eternalcode.core.libs.dev.rollczi")

    relocate("org.bstats", "com.eternalcode.core.libs.org.bstats")
    relocate("io.papermc.lib", "com.eternalcode.core.libs.io.papermc.lib")
    relocate("net.kyori", "com.eternalcode.core.libs.net.kyori")

    relocate("dev.triumphteam.gui", "com.eternalcode.core.libs.dev.triumphteam.gui")

    relocate("org.slf4j", "com.eternalcode.core.libs.org.slf4j")
    relocate("com.google.gson", "com.eternalcode.core.libs.com.google.gson")
}
