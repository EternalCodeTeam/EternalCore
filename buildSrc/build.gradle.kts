plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("net.kyori:blossom:1.3.1")
    implementation("com.gradleup.shadow:shadow-gradle-plugin:9.0.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.10")
    implementation("net.minecrell:plugin-yml:0.6.0")
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
