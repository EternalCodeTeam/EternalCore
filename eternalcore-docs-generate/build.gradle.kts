plugins {
    `eternalcode-java`
    `eternalcore-repositories`
}

dependencies {
    implementation(project(":eternalcore-core"))
    implementation(project(":eternalcore-docs-api"))

    implementation("com.google.guava:guava:32.1.2-jre")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("dev.rollczi:litecommands-core:3.0.0-BETA-pre21")

    runtimeOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    runtimeOnly("io.papermc:paperlib:1.0.8")
    runtimeOnly("net.kyori:adventure-platform-bukkit:4.3.0")
    runtimeOnly("net.kyori:adventure-text-minimessage:4.14.0")
    runtimeOnly("net.dzikoysk:cdn:1.14.4")
    runtimeOnly("com.j256.ormlite:ormlite-jdbc:6.1")
    runtimeOnly("com.zaxxer:HikariCP:5.0.1")
    runtimeOnly("dev.rollczi:litecommands-core:3.0.0-BETA-pre21")
    runtimeOnly("dev.rollczi:liteskullapi:1.3.0")
    runtimeOnly("org.panda-lang:expressible:1.3.6")
    runtimeOnly("org.panda-lang:panda-utilities:0.5.3-alpha")
    runtimeOnly("commons-io:commons-io:2.13.0")
    runtimeOnly("dev.triumphteam:triumph-gui:3.1.5")
    runtimeOnly("org.bstats:bstats-bukkit:3.0.2")
}
