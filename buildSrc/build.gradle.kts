plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("net.kyori:blossom:1.3.1")
    implementation("com.github.johnrengelman:shadow:8.1.1")
    implementation("xyz.jpenilla:run-task:2.1.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.21")
    implementation("net.minecrell:plugin-yml:0.5.3")
}

sourceSets {
    main {
        java.setSrcDirs(emptyList<String>())
        groovy.setSrcDirs(emptyList<String>())
        resources.setSrcDirs(emptyList<String>())
    }
    test {
        java.setSrcDirs(emptyList<String>())
        kotlin.setSrcDirs(emptyList<String>())
        groovy.setSrcDirs(emptyList<String>())
        resources.setSrcDirs(emptyList<String>())
    }
}
