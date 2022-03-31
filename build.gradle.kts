plugins {
    id("java-library")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

allprojects {
    group = "core"
    version = "1.0.3-BETA"

    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    java {
        withSourcesJar()
    }
}

subprojects {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
        maven { url = uri("https://repo.panda-lang.org/releases") }
        maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }

    publishing {
        publications {
            repositories {
                mavenLocal()
            }
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
