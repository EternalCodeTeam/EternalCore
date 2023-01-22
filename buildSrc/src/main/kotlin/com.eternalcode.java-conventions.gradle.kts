plugins {
    `java-library`
}

group = "com.eternalcode"
version = "1.0.0"

repositories {
    mavenLocal()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://repo.panda-lang.org/releases") }
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    maven { url = uri("https://repository.minecodes.pl/releases") }
    maven { url = uri("https://repository.minecodes.pl/snapshots") }
    maven { url = uri("https://repo.alessiodp.com/releases/") }
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
