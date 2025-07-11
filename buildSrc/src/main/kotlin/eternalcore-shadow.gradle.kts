plugins {
    `java-library`

    id("com.gradleup.shadow")
}

val extension = extensions.create<EternalShadowExtension>("eternalShadow")

afterEvaluate {
    dependencies {
        extension.compileOnlyDependencies.forEach {
            compileOnlyApi(it)
            testImplementation(it)
        }
    }
}
