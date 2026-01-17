import io.papermc.hangarpublishplugin.model.Platforms

plugins {
    id("com.gradleup.shadow")
    id("com.modrinth.minotaur")
    id("io.papermc.hangar-publish-plugin")
}

val buildNumber = providers.environmentVariable("GITHUB_RUN_NUMBER").orNull
val isRelease = providers.environmentVariable("GITHUB_EVENT_NAME").orNull == "release"

if (buildNumber != null && !isRelease) {
    val offset = try {
        val description = providers.exec {
            commandLine("git", "describe", "--tags", "--long")
        }.standardOutput.asText.get().trim()
        val parts = description.split("-")
        if (parts.size >= 3) parts[parts.size - 2] else "0"
    } catch (e: Exception) {
        try {
            providers.exec {
                commandLine("git", "rev-list", "--count", "HEAD")
            }.standardOutput.asText.get().trim()
        } catch (e: Exception) {
            "0"
        }
    }
    
    val baseVersion = project.version.toString().replace("-SNAPSHOT", "")
    version = "$baseVersion-SNAPSHOT+$offset"
}

val changelogText = providers.environmentVariable("CHANGELOG").orElse(providers.exec {
    commandLine("git", "log", "-1", "--format=%B")
}.standardOutput.asText)

logger.lifecycle("Building version: $version")

val paperVersions = (property("paperVersion") as String)
    .split(",")
    .map { it.trim() }

val hangarToken = providers.environmentVariable("HANGAR_API_TOKEN")
    .orElse(providers.gradleProperty("hangarToken"))

val projectVersion = project.version.toString()
val releaseChannel = if (isRelease) "Release" else "Snapshot"

afterEvaluate {
    val shadowJarTask = tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar")
    
    modrinth {
        token.set(providers.environmentVariable("MODRINTH_TOKEN"))
        projectId.set(project.findProperty("modrinthProjectId") as String? ?: project.name)
        versionNumber.set(projectVersion)
        versionType.set(if (isRelease) "release" else "beta")
        uploadFile.set(shadowJarTask)
        gameVersions.addAll(paperVersions)
        loaders.addAll(listOf("paper", "spigot", "folia", "purpur"))
        changelog.set(changelogText)
        
        val readmeFile = rootProject.file("README.md")
        if (readmeFile.exists()) {
            syncBodyFrom.set(readmeFile.readText())
        }
    }

    hangarPublish {
        publications.register("plugin") {
            version.set(projectVersion)
            channel.set(releaseChannel)
            changelog.set(changelogText)
            id.set(project.findProperty("hangarProjectId") as String? ?: project.name)
            apiKey.set(hangarToken)
            platforms {
                register(Platforms.PAPER) {
                    jar.set(shadowJarTask.flatMap { it.archiveFile })
                    platformVersions.set(paperVersions)
                }
            }
        }
    }
}
