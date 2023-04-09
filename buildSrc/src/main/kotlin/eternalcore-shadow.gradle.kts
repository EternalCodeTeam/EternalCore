import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    id("net.kyori.blossom")
    id("com.github.johnrengelman.shadow")
}

open class EternalShadowExtension {
    var jarName: String? = null

    val dependencyToLoad: MutableList<String> = mutableListOf()
    val relocateLibraries: MutableList<String> = mutableListOf()

    val compileOnlyDependencies: MutableList<String> = mutableListOf()
    val annotationProcessorDependencies: MutableList<String> = mutableListOf()
    val implementationDependencies: MutableList<Any> = mutableListOf()

    fun library(dependency: String) {
        dependencyToLoad.add(dependency)
        compileOnlyDependencies.add(dependency)
    }

    fun libraryRelocate(vararg relocations: String) {
        relocateLibraries.addAll(relocations)
    }

    fun annotationProcessor(dependency: String) {
        annotationProcessorDependencies.add(dependency)
    }

    fun module(dependency: Any) {
        implementationDependencies.add(dependency)
    }

    fun pluginApiHook(dependency: String) {
        compileOnlyDependencies.add(dependency)
    }

    fun compileOnly(dependency: String) {
        compileOnlyDependencies.add(dependency)
    }
}


val extension = extensions.create<EternalShadowExtension>("eternalShadow")

afterEvaluate {
    dependencies {
        extension.compileOnlyDependencies.forEach {
            compileOnly(it)
        }

        extension.annotationProcessorDependencies.forEach {
            annotationProcessor(it)
        }

        extension.implementationDependencies.forEach {
            implementation(it)
        }
    }

    blossom {
        val dependencyConstants = "src/main/java/com/eternalcode/core/loader/EternalCoreLoaderConstants.java"

        val rawDependencies =  extension.dependencyToLoad.joinToString("|")
            .replace(".", "{}")
        val rawRepositories = repositories
            .filterIsInstance(MavenArtifactRepository::class.java)
            .filter { it.url.toString().startsWith("https://") }
            .map { it.url }
            .joinToString("|")
        val rawRelocations = extension.relocateLibraries
            .joinToString("|")
            .replace(".", "{}")

        replaceToken("{eternalcore-dependencies}", rawDependencies, dependencyConstants)
        replaceToken("{eternalcore-repositories}", rawRepositories, dependencyConstants)
        replaceToken("{eternalcore-relocations}", rawRelocations, dependencyConstants)
    }

    tasks.withType<ShadowJar> {
        extension.jarName?.let {
            archiveFileName.set(it)
        }

        extension.relocateLibraries.forEach { pack ->
            relocate(pack, "com.eternalcode.core.libs.$pack")
        }
    }

}