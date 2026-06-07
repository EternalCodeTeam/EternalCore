plugins {
    `eternalcode-java`
    `eternalcore-repositories`
    `eternalcore-publish`
}

dependencies {
    compileOnlyApi("io.papermc.paper:paper-api:${Versions.PAPER_API}")
    api("org.jetbrains:annotations:${Versions.JETBRAINS_ANNOTATIONS}")
}
