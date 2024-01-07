plugins {
    `eternalcode-java`
    `eternalcore-repositories`
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:${Versions.PAPER_API}")
    compileOnly("io.papermc:paperlib:${Versions.PAPERLIB}")
}
