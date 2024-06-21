plugins {
    `java-library`

    id("io.github.goooler.shadow")
}

val extension = extensions.create<EternalShadowExtension>("eternalShadow")

afterEvaluate {
    dependencies {
        extension.compileOnlyDependencies.forEach {
            compileOnlyApi(it)
        }
    }
}
