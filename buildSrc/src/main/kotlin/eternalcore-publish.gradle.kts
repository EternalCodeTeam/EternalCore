plugins {
    `java-library`
    `maven-publish`
}

group = "com.eternalcode"
version = "1.1.0"

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    repositories {
        mavenLocal()

        maven(
            name = "eternalcode",
            url = "https://repo.eternalcode.pl",
            username = "ETERNAL_CODE_MAVEN_USERNAME",
            password = "ETERNAL_CODE_MAVEN_PASSWORD",
            snapshots = true,
        )
    }
}

fun RepositoryHandler.maven(
    name: String,
    url: String,
    username: String,
    password: String,
    snapshots: Boolean = true
) {
    val isSnapshot = version.toString().endsWith("-SNAPSHOT")

    if (isSnapshot && !snapshots) {
        return
    }

    this.maven {
        this.name =
            if (isSnapshot) "${name}Snapshots"
            else "${name}Releases"

        this.url =
            if (isSnapshot) uri("$url/snapshots")
            else uri("$url/releases")

        this.credentials {
            this.username = System.getenv(username)
            this.password = System.getenv(password)
        }
    }
}

interface EternalCorePublishExtension {
    var artifactId: String
}

val extension = extensions.create<EternalCorePublishExtension>("eternalCorePublish")

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                artifactId = extension.artifactId
                from(project.components["java"])
            }
        }
    }
}