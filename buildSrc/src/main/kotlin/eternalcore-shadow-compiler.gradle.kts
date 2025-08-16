import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.compile.JavaCompile

afterEvaluate {
    afterEvaluate()
}

plugins {
    `java-library`
    id("com.gradleup.shadow")
    id("net.minecrell.plugin-yml.bukkit")
}

open class EternalShadowCompilerExtension {

    internal var configureShadowJar: Action<ShadowJar>? = null
    internal var configureBukkit: Action<BukkitPluginDescription>? = null
    internal val modules: MutableList<String> = mutableListOf()

    fun module(project: String) {
        modules.add(project)
    }

    fun pluginYml(configure: Action<BukkitPluginDescription>) {
        this.configureBukkit = configure
    }

    fun shadowJar(configure: Action<ShadowJar>) {
        this.configureShadowJar = configure
    }

}

val eternalShadowCompiler = extensions.create<EternalShadowCompilerExtension>("eternalShadowCompiler")

fun afterEvaluate() {

    eternalShadowCompiler.modules.forEach { module ->
        val project = project(module)
        val extension = project.extensions.getByType(EternalShadowExtension::class.java)

        applyExtensionShadow(extension)
    }

    dependencies {
        eternalShadowCompiler.modules
            .map { project(it) }
            .forEach { project ->
                add("api", project)
            }
    }

    eternalShadowCompiler.configureBukkit?.execute(extensions.getByType<BukkitPluginDescription>())

    tasks.withType<ShadowJar> {
        eternalShadowCompiler.configureShadowJar?.execute(this)
    }
}

fun applyExtensionShadow(eternalShadow: EternalShadowExtension) {
    val cleanGeneratedSources = tasks.register<Delete>("cleanEternalCoreGeneratedSources") {
        delete(layout.buildDirectory.dir("generated/sources/eternalcore"))
    }

    val processEternalCoreConstants = tasks.register("processEternalCoreConstants") {
        dependsOn(cleanGeneratedSources)

        val sourceFile = file("src/main/java/com/eternalcode/core/loader/EternalCoreLoaderConstants.java")
        val outputDir = layout.buildDirectory.dir("generated/sources/eternalcore/com/eternalcode/core/loader")
        val outputFile = outputDir.get().asFile.resolve("EternalCoreLoaderConstants.java")

        inputs.file(sourceFile)
        outputs.file(outputFile)

        val rawDependencies = eternalShadow.libraries.joinToString("|")
            .replace(".", "{}")
        val rawRepositories = repositories
            .filterIsInstance<MavenArtifactRepository>()
            .filter { it.url.toString().startsWith("https://") }
            .map { it.url }
            .joinToString("|")
        val rawRelocations = eternalShadow.relocations
            .joinToString("|")
            .replace(".", "{}")

        inputs.property("dependencies", rawDependencies)
        inputs.property("repositories", rawRepositories)
        inputs.property("relocations", rawRelocations)

        doLast {
            if (sourceFile.exists()) {
                outputFile.parentFile.mkdirs()
                val content = sourceFile.readText()
                    .replace("{eternalcore-dependencies}", rawDependencies)
                    .replace("{eternalcore-repositories}", rawRepositories)
                    .replace("{eternalcore-relocations}", rawRelocations)
                outputFile.writeText(content)
            }
        }
    }

    extensions.configure<SourceSetContainer> {
        named("main") {
            java {
                srcDir(layout.buildDirectory.dir("generated/sources/eternalcore"))
            }
        }
    }

    tasks.named<JavaCompile>("compileJava") {
        dependsOn(processEternalCoreConstants)

        val s = File.separator
        source = source.filter { file ->
            !file.path.contains("src${s}main${s}java${s}com${s}eternalcode${s}core${s}loader${s}EternalCoreLoaderConstants.java")
        }.asFileTree
    }

    tasks.withType<ShadowJar> {
        eternalShadow.relocations.forEach { pack ->
            relocate(pack, "com.eternalcode.core.libs.$pack")
        }
    }
}
