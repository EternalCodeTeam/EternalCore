plugins {
    `java-library`
}

dependencies {
    testImplementation("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
    testImplementation("net.dzikoysk:cdn:1.14.4")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.17")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")
    testImplementation("org.mockito:mockito-core:5.3.1")
    testImplementation("net.kyori:adventure-platform-bukkit:4.3.0")
    testImplementation("net.kyori:adventure-text-minimessage:4.13.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
