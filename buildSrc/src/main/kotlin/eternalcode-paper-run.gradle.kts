
plugins {
    `java-library`
    id("xyz.jpenilla.run-paper")
}

tasks {
    runServer {
        minecraftVersion("1.19.4")
    }
}