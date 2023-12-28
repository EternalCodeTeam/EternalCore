plugins {
    `java-library`
}

dependencies {
    testImplementation("dev.rollczi:litecommands-core:3.2.2")
    testImplementation("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
    testImplementation("net.dzikoysk:cdn:1.14.4")
    testImplementation("org.panda-lang:expressible-junit:1.3.6")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.20")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")
    testImplementation("org.mockito:mockito-core:5.8.0")
    testImplementation("net.kyori:adventure-platform-bukkit:4.3.2")
    testImplementation("net.kyori:adventure-platform-facet:4.3.2")
    testImplementation("net.kyori:adventure-text-minimessage:4.15.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
