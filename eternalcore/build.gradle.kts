plugins {
    java
}

dependencies {
    // lombok
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    // annotations for plugins
    compileOnly("org.spigotmc:plugin-annotations:1.2.3-SNAPSHOT")
    annotationProcessor("org.spigotmc:plugin-annotations:1.2.3-SNAPSHOT")

    // spigot api & kyori adventure
    compileOnly("org.spigotmc:spigot:1.18.2-R0.1-SNAPSHOT")
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

    // ormlite jdbc
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")

    // FastBoard
    implementation("fr.mrmicky:fastboard:1.2.1")

    // TriumphGui
    implementation("dev.triumphteam:triumph-gui:3.1.2")

    // tests
    testImplementation("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.10")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.withType <com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("EternalCore v${project.version} (MC 1.17-1.18x).jar")

    exclude("org/intellij/lang/annotations/**")
    exclude("org/jetbrains/annotations/**")
    exclude("javax/**")

    relocate("net.dzikoysk", "core.libs.net.dzikoysk")
    relocate("dev.rollczi", "core.libs.dev.rollczi")
    relocate("org.bstats", "core.libs.org.bstats")
    relocate("org.panda_lang", "core.libs.panda")
    relocate("fr.mrmicky.fastboard", "core.libs.fastboard")
    relocate("panda", "core.libs.panda")
    relocate("com.j256.ormlite", "core.libs.ormlite")
    relocate("dev.triumphteam.gui", "core.libs.gui")
    relocate("com.google.gson", "core.libs.com.google.gson")
}
