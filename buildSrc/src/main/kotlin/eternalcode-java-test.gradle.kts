plugins {
    `java-library`
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:${Versions.JUNIT_BOM}"))

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.testcontainers:junit-jupiter:${Versions.TEST_CONTAINERS}")
    testImplementation("org.testcontainers:mysql:${Versions.TEST_CONTAINERS}")
    testImplementation("mysql:mysql-connector-java:${Versions.MYSQL_CONNECTOR}")
    testImplementation("org.mockito:mockito-core:${Versions.MOCKITO_CORE}")
    testImplementation("net.kyori:adventure-platform-facet:${Versions.ADVENTURE_PLATFORM}")
    testImplementation("org.spigotmc:spigot-api:${Versions.SPIGOT_API}")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
