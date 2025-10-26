plugins {
    `eternalcode-java`
    `eternalcore-repositories`
    `eternalcore-shadow-compiler`
    id("dev.rollczi.litegration.paper") version Versions.LITEGRATION
    id("xyz.jpenilla.run-paper")
}

repositories {
    maven("https://repo.eternalcode.pl/releases/")
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

tasks.testPaper {
    eula = true
    serverVersion = "1.21.7"
}

dependencies {
    implementation("com.spotify:completable-futures:${Versions.SPOTIFY_COMPLETABLE_FUTURES}")

    testPaperImplementation(project(":eternalcore-core"))
    testPaperImplementation(project(":eternalcore-api"))

    testPaperImplementation("dev.rollczi:litegration-client-mcprotocollib:${Versions.LITEGRATION}")

    testPaperImplementation(platform("org.junit:junit-bom:6.0.0"))
    testPaperImplementation("org.spigotmc:spigot-api:${Versions.SPIGOT_API}")

    testPaperImplementation("org.assertj:assertj-core:3.27.6")
}

tasks {
    runServer {
        minecraftVersion("1.21.8")
        downloadPlugins.modrinth("luckperms", "v${Versions.LUCKPERMS}-bukkit")
    }
}
