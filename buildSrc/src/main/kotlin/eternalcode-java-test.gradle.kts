plugins {
    `java-library`
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:${Versions.JUNIT_BOM}"))

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.testcontainers:junit-jupiter:1.21.3")
    testImplementation("org.testcontainers:mysql:1.21.3")
    testImplementation("mysql:mysql-connector-java:8.0.33")
    testImplementation("org.mockito:mockito-core:${Versions.MOCKITO_CORE}")
    testImplementation("net.kyori:adventure-platform-facet:${Versions.ADVENTURE_PLATFORM}")
    testImplementation("org.spigotmc:spigot-api:${Versions.SPIGOT_API}")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
