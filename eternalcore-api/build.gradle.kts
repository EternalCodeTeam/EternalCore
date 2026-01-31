plugins {
    `eternalcode-java`
    `eternalcore-repositories`
    `eternalcore-publish`
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:${Versions.SPIGOT_API}")
    api("org.jetbrains:annotations:${Versions.JETBRAINS_ANNOTATIONS}")
    api("org.jspecify:jspecify:${Versions.JSPECIFY}")
}
