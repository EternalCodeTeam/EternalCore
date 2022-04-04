plugins {
    id("java-library")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

dependencies {
    // TODO: Full spigot compatibly

    // paper lib, spigot api & kyori adventure
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    //compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")

    //api("io.papermc:paperlib:1.0.7")

    implementation("net.kyori:adventure-platform-bukkit:4.1.0")
    implementation("net.kyori:adventure-text-minimessage:4.10.1")

    // LiteCommands
    implementation("dev.rollczi.litecommands:bukkit:1.8.4")

    // cdn configs
    implementation("net.dzikoysk:cdn:1.13.20")

    // expressible
    implementation("org.panda-lang:expressible:1.1.17")

    // bStats
    implementation("org.bstats:bstats-bukkit:3.0.0")

    // HikariCP
    //implementation("com.j256.ormlite:ormlite-jdbc:6.1")
    implementation("com.zaxxer:HikariCP:5.0.1")


    // FastBoard
    implementation("fr.mrmicky:fastboard:1.2.1")

    // TriumphGui
    implementation("dev.triumphteam:triumph-gui:3.1.2")

    // tests
    testImplementation("org.spigotmc:spigot:1.18.2-R0.1-SNAPSHOT")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.10")
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
    apiVersion = "1.17"
    prefix = "EternalCore"
    author = "EternalCodeTeam"
    name = "EternalCore"
    description = "Essential plugin for your server!"
    version = "${project.version}"
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("EternalCore v${project.version} (MC 1.17-1.18x).jar")

    exclude("org/intellij/lang/annotations/**")
    exclude("org/jetbrains/annotations/**")
    exclude("javax/**")
    exclude("META-INF/**")


    relocate("net.dzikoysk", "com.eternalcode.core.libs.net.dzikoysk")
    relocate("dev.rollczi", "com.eternalcode.core.libs.dev.rollczi")
    relocate("org.bstats", "com.eternalcode.core.libs.org.bstats")
    relocate("org.panda_lang", "com.eternalcode.core.libs.org.panda_lang")
    relocate("fr.mrmicky.fastboard", "com.eternalcode.core.libs.fr.mrmicky.fastboard")
    relocate("panda", "com.eternalcode.core.libs.panda")
    relocate("dev.triumphteam.gui", "com.eternalcode.core.libs.dev.triumphteam.gui")
    relocate("com.google.gson", "com.eternalcode.core.libs.com.google.gson")
    relocate("com.zaxxer", "com.eternalcode.core.libs.com.zaxxer")
    relocate("org.sl4j", "com.eternalcode.core.libs.org.sl4j")

    //relocate("io.papermc.lib", "com.eternalcode.core.libs.paperlib")
    //relocate("com.j256.ormlite", "com.eternalcode.core.libs.ormlite")
}
