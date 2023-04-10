plugins {
    `eternalcode-java`
    `eternalcore-repositories`
    `eternalcore-shadow`
    id("xyz.jpenilla.run-paper") version "2.0.1"
}

dependencies {
    implementation(project(":eternalcore-core"))
}

tasks {
    runServer {
        minecraftVersion("1.19.3")
    }
}