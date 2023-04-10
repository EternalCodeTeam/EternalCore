plugins {
    `java-library`

    id("com.github.johnrengelman.shadow")
}

val extension = extensions.create<EternalShadowExtension>("eternalShadow")

afterEvaluate {
    dependencies {
        extension.implementationDependencies.forEach {
            api(it)
        }

        extension.compileOnlyDependencies.forEach {
            compileOnlyApi(it)
        }

        extension.annotationProcessorDependencies.forEach {
            annotationProcessor(it)
        }

        extension.modules.forEach {
            api(project(it))
        }
    }
}