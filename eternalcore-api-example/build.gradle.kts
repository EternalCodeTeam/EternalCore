plugins {
    `eternalcode-java`
    `eternalcore-repositories`

    id("com.github.johnrengelman.shadow")
    id("net.minecrell.plugin-yml.bukkit")
    id("xyz.jpenilla.run-paper") version "2.2.3"
}

dependencies {
    implementation("dev.rollczi:litecommands-bukkit:${Versions.LITE_COMMANDS}")

    compileOnly("org.spigotmc:spigot-api:${Versions.SPIGOT_API}")
    compileOnly(project(":eternalcore-api")) // This is the eternalcore-api module
}

bukkit {
    main = "com.eternalcode.example.EternalCoreApiExamplePlugin"
    apiVersion = "1.13"
    prefix = "EternalCore-Api-Example"
    author = "EternalCodeTeam"
    name = "EternalCore-Api-Example"
    description = "Test plugin for testing eternalcore developer api!"
    website = "www.eternalcode.pl"
    version = "${project.version}"
    depend = listOf("EternalCore")
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.shadowJar {
    archiveFileName.set("EternalCore-Api-Example v${project.version}.jar")

    exclude(
        "META-INF/**",
    )
}