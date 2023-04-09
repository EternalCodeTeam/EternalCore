plugins {
    `eternalcode-java`
    `eternalcode-java-test`
    `eternalcore-repositories`
    `eternalcore-shadow`

    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
}

bukkit {
    main = "com.eternalcode.core.loader.EternalCoreLoader"
    apiVersion = "1.13"
    prefix = "EternalCore"
    author = "EternalCodeTeam"
    name = "EternalCore"
    description = "All the most important server functions in one!"
    website = "www.eternalcode.pl"
    version = "${project.version}"
    softDepend = listOf("PlaceholderAPI")
}

dependencies {
    // modules
    api(project(":eternalcore-api"))
    api(project(":eternalcore-paper"))

    // Spigot API
    compileOnlyApi("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")

    // lombok
    compileOnlyApi("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    // hooks
    compileOnly("me.clip:placeholderapi:2.11.3")
}

eternalShadow {
    jarName = "EternalCore v${project.version} (MC 1.17-1.19x).jar"

    library("io.papermc:paperlib:1.0.8")
    library("net.kyori:adventure-platform-bukkit:4.3.0")
    library("net.kyori:adventure-text-minimessage:4.13.0")
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
        "com.j256.ormlite",
        "com.zaxxer.hikari",
        "org.slf4j"
    )

    // command framework
    library("dev.rollczi.litecommands:bukkit-adventure:2.8.7")
    libraryRelocate("dev.rollczi.litecommands")

    // skull api
    library("dev.rollczi:liteskullapi:1.3.0")
    libraryRelocate("dev.rollczi.liteskullapi")

    // utility library
    library("org.panda-lang:expressible:1.3.1")
    library("org.panda-lang:panda-utilities:0.5.3-alpha")
    libraryRelocate(
        "panda.std",
        "panda.utilities",
    )

    // gui library
    library("dev.triumphteam:triumph-gui:3.1.4")
    libraryRelocate("dev.triumphteam")

    // metrics
    library("org.bstats:bstats-bukkit:3.0.2")
    libraryRelocate("org.bstats")

    // commons
    library("commons-io:commons-io:2.11.0")
    libraryRelocate("org.apache.commons.io")
}