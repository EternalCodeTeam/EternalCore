plugins {
    id("java")
}

group = "com.eternalcode"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
