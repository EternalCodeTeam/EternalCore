import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

dependencies {
    // paper lib, spigot api
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
    api("io.papermc:paperlib:1.0.7")

    // Kyori Adventure
    implementation("net.kyori:adventure-platform-bukkit:4.1.1")
    implementation("net.kyori:adventure-text-minimessage:4.11.0")

    // LiteCommands
    implementation("dev.rollczi.litecommands:bukkit:2.1.1")

    // cdn configs
    implementation("net.dzikoysk:cdn:1.13.23")

    // expressible
    implementation("org.panda-lang:expressible:1.1.20")

    // bStats
    implementation("org.bstats:bstats-bukkit:3.0.0")

    // HikariCP
    implementation("com.zaxxer:HikariCP:5.0.1")

    // FastBoard
    implementation("fr.mrmicky:fastboard:1.2.1")

    // TriumphGui
    implementation("dev.triumphteam:triumph-gui:3.1.2")

    // tests
    testImplementation("org.spigotmc:spigot:1.18.2-R0.1-SNAPSHOT")
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

    relocate("fr.mrmicky.fastboard", "com.eternalcode.core.libs.fr.mrmicky.fastboard")
    relocate("dev.triumphteam.gui", "com.eternalcode.core.libs.dev.triumphteam.gui")

    relocate("com.zaxxer", "com.eternalcode.core.libs.com.zaxxer")
    relocate("org.slf4j", "com.eternalcode.core.libs.org.slf4j")
    relocate("com.google.gson", "com.eternalcode.core.libs.com.google.gson")
}
