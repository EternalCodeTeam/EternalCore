plugins {
    id("eternalcode.java-conventions")
    id("com.github.johnrengelman.shadow") version "8.1.0"
    id("java")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    api("org.panda-lang:expressible:1.3.1")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}