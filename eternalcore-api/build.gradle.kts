plugins {
    `eternalcode-java`
    `eternalcore-repositories`
    `eternalcore-publish`
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:${Versions.SPIGOT_API}")
    compileOnly("com.eternalcode:eternalcode-commons-bukkit:${Versions.ETERNALCODE_COMMONS}")
    api("org.jetbrains:annotations:${Versions.JETBRAINS_ANNOTATIONS}")
}
