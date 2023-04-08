import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("eternalcode.java-conventions")
    id("com.github.johnrengelman.shadow") version "8.1.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
    id("xyz.jpenilla.run-paper") version "2.0.1"
    id("net.kyori.blossom") version "1.2.0"
}

dependencies {
    implementation(project(":core"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

bukkit {
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


tasks {
    runServer {
        minecraftVersion("1.19.3")
    }
}

tasks.withType<ShadowJar> {
    archiveFileName.set("EternalCore v${project.version} (MC 1.17-1.19x).jar")

    exclude(
        "org/intellij/lang/annotations/**",
        "org/jetbrains/annotations/**",
        "org/checkerframework/**",
        "META-INF/**",
        "javax/**"
    )

    val prefix = "com.eternalcode.core.libs"
    listOf(
        "panda.std",
        "panda.utilities",
        "org.panda_lang",
        "org.bstats",
        "net.dzikoysk",
        "dev.rollczi",
        "net.kyori",
        "io.papermc.lib",
        "dev.triumphteam",
        "org.slf4j",
        "com.google.gson",
        "com.eternalcode.containers"
    ).forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }
}

blossom {
    val dependencyConstants = "src/main/java/com/eternalcode/core/loader/EternalCoreLoaderConstants.java"

    val dependencyToLoad: MutableList<String> by project(":core").extra
    val relocateLibraries: MutableList<String> by project(":core").extra

    val rawDependencies = dependencyToLoad.joinToString("|")
        .replace(".", "{}")
    val rawRepositories = repositories
        .filterIsInstance(MavenArtifactRepository::class.java)
        .filter { it.url.toString().startsWith("https://") }
        .map { it.url }
        .joinToString("|")
    val rawRelocations = relocateLibraries
        .joinToString("|")
        .replace(".", "{}")

    replaceToken("{eternalcore-dependencies}", rawDependencies, dependencyConstants)
    replaceToken("{eternalcore-repositories}", rawRepositories, dependencyConstants)
    replaceToken("{eternalcore-relocations}", rawRelocations, dependencyConstants)
}

description = "plugin"
