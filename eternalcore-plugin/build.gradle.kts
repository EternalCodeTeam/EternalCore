plugins {
    `eternalcode-java`
    `eternalcore-repositories`
    `eternalcore-shadow-compiler`
    id("xyz.jpenilla.run-paper") version "2.3.0"
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
        softDepend = listOf("PlaceholderAPI")
    }

    shadowJar {
        archiveFileName.set("EternalCore v${project.version} (MC 1.17.x-1.20.x).jar")

        exclude(
            "META-INF/**",
        )

        dependsOn(":eternalcore-core:test")
        dependsOn(":eternalcore-core:checkstyleMain")
    }
}

tasks {
    runServer {
        minecraftVersion("1.20.4")
    }
}
