plugins {
    `java-library`

    id("com.github.johnrengelman.shadow")
}

val extension = extensions.create<EternalShadowExtension>("eternalShadow")

afterEvaluate {
    dependencies {
        extension.compileOnlyDependencies.forEach {
            compileOnlyApi(it)
        }
    }
}