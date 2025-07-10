plugins {
    `java-library`
}

dependencies {
    testImplementation("org.spigotmc:spigot-api:${Versions.SPIGOT_API}")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.JUNIT_JUPITER_API}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${Versions.JUNIT_JUPITER_PARAMS}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.JUNIT_JUPITER_ENGINE}")

    testImplementation("org.mockito:mockito-core:${Versions.MOCKITO_CORE}")
    testImplementation("net.kyori:adventure-platform-facet:${Versions.ADVENTURE_PLATFORM}")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
