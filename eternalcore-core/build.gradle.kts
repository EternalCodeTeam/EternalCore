plugins {
    `eternalcode-java`
    `eternalcode-java-test`
    `eternalcore-repositories`
    `eternalcore-shadow`
}

eternalShadow {
    // modules
    module(":eternalcore-api")
    module(":eternalcore-paper")
    module(":eternalcore-docs-api")

    // Minecraft API
    onlyCompile("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")

    onlyCompile("me.clip:placeholderapi:2.11.3")
    onlyCompile("org.projectlombok:lombok:1.18.26")
    onlyCompile("org.jetbrains:annotations:24.0.1")
    annotationProcessors("org.projectlombok:lombok:1.18.26")

    // Paper and Adventure libraries
    library("io.papermc:paperlib:1.0.8")
    library("net.kyori:adventure-platform-bukkit:4.3.0")
    library("net.kyori:adventure-text-minimessage:4.13.1")
    libraryRelocate(
        "io.papermc.lib",
        "net.kyori",
        "com.google.gson",
    )

    // configuration
    library("net.dzikoysk:cdn:1.14.4")
    libraryRelocate(
        "net.dzikoysk.cdn"
    )

    // database
    library("com.j256.ormlite:ormlite-jdbc:6.1")
    library("com.zaxxer:HikariCP:5.0.1")
    libraryRelocate(
        "org.slf4j"
    )

    // command framework & skull library
    library("dev.rollczi.litecommands:bukkit-adventure:2.8.7")
    library("dev.rollczi:liteskullapi:1.3.0")
    libraryRelocate(
        "dev.rollczi.litecommands",
        "dev.rollczi.liteskullapi"
    )

    // common libraries
    library("org.panda-lang:expressible:1.3.4")
    library("org.panda-lang:panda-utilities:0.5.3-alpha")
    library("commons-io:commons-io:2.11.0")
    libraryRelocate(
        "panda.std",
        "panda.utilities",
        "org.apache.commons.io",
    )

    // gui library
    library("dev.triumphteam:triumph-gui:3.1.5")
    libraryRelocate("dev.triumphteam")

    // metrics
    library("org.bstats:bstats-bukkit:3.0.2")
    libraryRelocate("org.bstats")
}
