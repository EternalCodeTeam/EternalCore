plugins {
    id("java-library")
}

dependencies {
    // annotations for plugins
    compileOnly("org.spigotmc:plugin-annotations:1.2.3-SNAPSHOT")
    annotationProcessor("org.spigotmc:plugin-annotations:1.2.3-SNAPSHOT")

    // paper api & kyori adventure
    compileOnly("org.spigotmc:spigot:1.18.2-R0.1-SNAPSHOT")
    implementation("net.kyori:adventure-platform-bukkit:4.1.0")
    implementation("net.kyori:adventure-text-minimessage:4.10.1")

    // LiteCommands
    implementation("dev.rollczi.litecommands:bukkit:1.8.4")

    // cdn configs
    implementation("net.dzikoysk:cdn:1.13.21")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
