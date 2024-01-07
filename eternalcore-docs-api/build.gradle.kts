plugins {
    `eternalcode-java`
    `eternalcore-repositories`
}

dependencies {
    compileOnly("com.google.guava:guava:${Versions.GUAVA}")
    compileOnly("com.google.code.gson:gson:${Versions.GSON}")
    compileOnly("dev.rollczi:litecommands-framework:${Versions.LITE_COMMANDS}")
}
