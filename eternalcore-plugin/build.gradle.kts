plugins {
    `eternalcode-java`
    `eternalcore-repositories`
    `eternalcore-shadow-compiler`
    `eternalcode-paper-run`
}

eternalShadowCompiler {
    shadowJar {
        archiveFileName.set("EternalCore v${project.version} (MC 1.17-1.19x).jar")
    }

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

    module(":eternalcore-core")
}