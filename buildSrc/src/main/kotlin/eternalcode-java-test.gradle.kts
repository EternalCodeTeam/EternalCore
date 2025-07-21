plugins {
    `java-library`
}

dependencies {
    testImplementation("dev.rollczi:litecommands-core:${Versions.LITE_COMMANDS}")
    testImplementation("org.spigotmc:spigot-api:${Versions.SPIGOT_API}")
    testImplementation("net.dzikoysk:cdn:${Versions.CDN_CONFIGS}")
    testImplementation("org.panda-lang:expressible-junit:${Versions.EXPRESSIBLE_JUNIT}")
    testImplementation("org.codehaus.groovy:groovy-all:${Versions.GROOVY_ALL}")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.JUNIT_JUPITER_API}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${Versions.JUNIT_JUPITER_PARAMS}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.JUNIT_JUPITER_ENGINE}")

    testImplementation("org.mockito:mockito-core:${Versions.MOCKITO_CORE}")
    testImplementation("net.kyori:adventure-platform-bukkit:${Versions.ADVENTURE_PLATFORM}")
    testImplementation("net.kyori:adventure-platform-facet:${Versions.ADVENTURE_PLATFORM}")
    testImplementation("net.kyori:adventure-text-minimessage:${Versions.ADVENTURE_TEXT_MINIMESSAGE}")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}