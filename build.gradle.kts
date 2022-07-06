plugins {
    id("java-library")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

allprojects {
    group = "com.eternalcode"
    version = "1.0.0"

    apply(plugin = "java-library")
    apply(plugin = "com.github.johnrengelman.shadow")

    java {
        withSourcesJar()
    }
}

subprojects {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
        maven { url = uri("https://repo.panda-lang.org/releases") }
        maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
       // maven { url = uri("https://repo.eternalcode.pl/releases") }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
