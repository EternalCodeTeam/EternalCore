plugins {
    `java-library`
    `eternalcode-java`
    `eternalcore-repositories`
    id("com.github.johnrengelman.shadow")
    id("net.minecrell.plugin-yml.bukkit")
}

group = "com.eternalcode.core"
version = "1.0.0"

repositories {
    maven("https://jitpack.io")
}

dependencies {
    compileOnly(project(":eternalcore-api"))

    compileOnly("org.spigotmc:spigot-api:${Versions.SPIGOT_API}")

    implementation("net.dzikoysk:cdn:${Versions.CDN_CONFIGS}")
    implementation("com.github.unldenis:holoeasy:3.1.1")
    implementation("com.eternalcode:eternalcode-commons-adventure:1.1.1")
    implementation("com.eternalcode:eternalcode-commons-shared:1.1.1")
}

bukkit {
    main = "com.eternalcode.core.addon.afk.AfkAddonPlugin"
    apiVersion = "1.13"
    prefix = "EternalCore-AfkAddon"
    author = "P1otrulla"
    name = "EternalCore-DiscordSpyAddon"
    description = "AfkAddon for EternalCore!"
    website = "https://eternalcode.pl"
    version = "${project.version}"
    depend = listOf("EternalCore", "ProtocolLib")
}

tasks.shadowJar {
    archiveFileName.set("EternalCore-AfkAddon v${project.version}.jar")

    listOf(
        "com.github.unldenis",
        "net.dzikoysk"
    ).forEach {
        relocate(it, "com.eternalcode.core.addon.afk.shared.$it")
    }
}



