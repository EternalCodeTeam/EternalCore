plugins {
    id("eternalcode.java-conventions")
}

val dependencyToLoad by extra(mutableListOf<String>())
val relocateLibraries by extra(mutableListOf<String>())

dependencies {
    // modules
    implementation(project(":api"))
    implementation(project(":paper"))

    // minecraft development api
    compileOnlyApi("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")

    library("io.papermc:paperlib:1.0.8")
    library("net.kyori:adventure-platform-bukkit:4.3.0")
    library("net.kyori:adventure-text-minimessage:4.13.0")

    // configuration
    library("net.dzikoysk:cdn:1.14.4")
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    // database
    library("com.j256.ormlite:ormlite-jdbc:6.1")
    library("com.zaxxer:HikariCP:5.0.1")

    // command framework
    library("dev.rollczi.litecommands:bukkit-adventure:2.8.7")

    // skull api
    library("dev.rollczi:liteskullapi:1.3.0")

    // utility library
    library("org.panda-lang:expressible:1.3.1")

    // gui library
    library("dev.triumphteam:triumph-gui:3.1.4")

    // metrics
    library("org.bstats:bstats-bukkit:3.0.2")

    relocate(
        "panda.std",
        "panda.utilities",
        "org.panda_lang",
        "org.bstats",
        "net.dzikoysk",
        "dev.rollczi",
        "net.kyori",
        "io.papermc.lib",
        "dev.triumphteam",
        "org.slf4j",
        "com.google.gson",
    )

    // bridge (hook)
    compileOnly("me.clip:placeholderapi:2.11.3")

    // commons
    library("commons-io:commons-io:2.11.0")

    // unit test
    testImplementation("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.17")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
    testImplementation("org.mockito:mockito-core:5.2.0")
    testImplementation("net.kyori:adventure-platform-bukkit:4.3.0")
    testImplementation("net.kyori:adventure-text-minimessage:4.13.0")
}

fun library(dependency: String) {
    val dependencyToLoad: MutableList<String> by extra

    dependencyToLoad.add(dependency)
    dependencies {
        compileOnly(dependency)
    }
}

fun relocate(vararg relocations: String) {
    val relocateLibraries: MutableList<String> by extra

    relocateLibraries.addAll(relocations)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

