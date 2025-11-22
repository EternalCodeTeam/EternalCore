plugins {
    `eternalcode-java`
    `eternalcore-repositories`
    `eternalcore-shadow-compiler`
    id("xyz.jpenilla.run-paper") version "3.0.2"
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
        archiveFileName.set("EternalCore v${project.version} (MC 1.17.x-1.21.x).jar")

        exclude(
            "META-INF/**",
        )
    }
}

dependencies {
    implementation("com.spotify:completable-futures:${Versions.SPOTIFY_COMPLETABLE_FUTURES}")
}

tasks {
    runServer {
        minecraftVersion("1.21.10")
        downloadPlugins.modrinth("luckperms", "v${Versions.LUCKPERMS}-bukkit")
    }
}
