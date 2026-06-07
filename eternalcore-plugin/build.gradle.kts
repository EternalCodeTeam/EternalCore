import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JavaToolchainService

plugins {
    `eternalcode-java`
    `eternalcore-repositories`
    `eternalcore-shadow-compiler`
    `eternalcore-publish-plugin`
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

ext {
    set("modrinthProjectId", "eternalcore")
    set("hangarProjectId", "eternalcore")
}

eternalShadowCompiler {
    module(":eternalcore-core")

    pluginYml {
        main = "com.eternalcode.core.loader.EternalCoreLoader"
        apiVersion = "1.13"
        prefix = "EternalCore"
        author = "EternalCodeTeam"
        name = "EternalCore"
        description = "All the most important server functions in one!"
        website = "www.eternalcode.pl"
        version = "${project.version}"
        softDepend = listOf("PlaceholderAPI", "dynmap")
        foliaSupported = true
    }

    shadowJar {
        archiveFileName.set("EternalCore v${project.version}.jar")

        exclude(
            "META-INF/**",
        )
    }
}

dependencies {
    implementation("com.spotify:completable-futures:${Versions.SPOTIFY_COMPLETABLE_FUTURES}")
}

tasks {
    val javaToolchains = extensions.getByType<JavaToolchainService>()

    runServer {
        javaLauncher.set(javaToolchains.launcherFor {
            languageVersion.set(JavaLanguageVersion.of(25))
        })

        minecraftVersion("26.1.2")
        downloadPlugins.modrinth("luckperms", "v${Versions.LUCKPERMS}-bukkit")
    }
}

