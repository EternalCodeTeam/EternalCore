plugins {
    `eternalcode-java`
    `eternalcore-repositories`
}

dependencies {
    implementation(project(":eternalcore-core"))
    implementation(project(":eternalcore-docs-api"))

    implementation("com.google.guava:guava:${Versions.GUAVA}")
    implementation("com.google.code.gson:gson:${Versions.GSON}")
    implementation("dev.rollczi:litecommands-framework:${Versions.LITE_COMMANDS}")

    runtimeOnly("org.spigotmc:spigot-api:${Versions.SPIGOT_API}")
    runtimeOnly("io.papermc:paperlib:${Versions.PAPERLIB}")
    runtimeOnly("net.kyori:adventure-platform-bukkit:${Versions.ADVENTURE_PLATFORM}")
    runtimeOnly("net.kyori:adventure-text-minimessage:${Versions.ADVENTURE_TEXT_MINIMESSAGE}")
    runtimeOnly("net.dzikoysk:cdn:${Versions.CDN_CONFIGS}")
    runtimeOnly("com.j256.ormlite:ormlite-jdbc:${Versions.ORMLITE}")
    runtimeOnly("com.zaxxer:HikariCP:${Versions.HIKARI_CP}")
    runtimeOnly("dev.rollczi:litecommands-core:${Versions.LITE_COMMANDS}")
    runtimeOnly("dev.rollczi:liteskullapi:${Versions.LITE_SKULL_API}")
    runtimeOnly("org.panda-lang:expressible:${Versions.EXPRESSIBLE}")
    runtimeOnly("org.panda-lang:panda-utilities:${Versions.PANDA_UTILITIES}")
    runtimeOnly("commons-io:commons-io:${Versions.APACHE_COMMONS}")
    runtimeOnly("dev.triumphteam:triumph-gui:${Versions.TRIUMPH_GUI}")
    runtimeOnly("org.bstats:bstats-bukkit:${Versions.BSTATS}")
}
