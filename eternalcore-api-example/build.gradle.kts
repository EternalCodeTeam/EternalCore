plugins {
    `java-library`

    id("com.gradleup.shadow")
    id("net.minecrell.plugin-yml.bukkit")
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "com.eternalcode"
version = "1.3.2"

repositories {
    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") // spigot
    maven("https://repo.panda-lang.org/releases/") // expressible

    // maven("https://repo.eternalcode.pl/releases/") // eternalcode
    // uncomment the line above if you want to use eternalcode repository
}

dependencies {
    implementation("dev.rollczi:litecommands-bukkit:${Versions.LITE_COMMANDS}")

    compileOnly("org.spigotmc:spigot-api:${Versions.SPIGOT_API}")
    compileOnly(project(":eternalcore-api")) // <-- This is the eternalcore-api module,
    // replace it with compileOnly("com.eternalcode:eternalcore-api:<version>")
}

bukkit {
    main = "com.eternalcode.example.EternalCoreApiExamplePlugin"
    apiVersion = "1.13"
    prefix = "EternalCore-Api-Example"
    author = "EternalCodeTeam"
    name = "EternalCore-Api-Example"
    description = "Test plugin for testing eternalcore developer api!"
    website = "www.eternalcode.pl"
    version = "${project.version}"
    depend = listOf("EternalCore")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.withType<JavaCompile> {
    options.compilerArgs = listOf("-Xlint:deprecation", "-parameters")
    options.encoding = "UTF-8"
}

tasks.shadowJar {
    archiveFileName.set("EternalCore-Api-Example v${project.version}.jar")

    exclude(
        "META-INF/**",
        "org/jetbrains/annotations/**",
        "org/intellij/**",
    )

    val libs = "com.eternalcode.example.libs"
    listOf(
        "dev.rollczi",
        "panda"
    ).forEach {
        relocate(it, "$libs.$it")
    }
}
