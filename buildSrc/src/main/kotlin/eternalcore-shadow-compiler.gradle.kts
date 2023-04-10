import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

afterEvaluate {
    afterEvaluate()
}

plugins {
    `java-library`
    id("net.kyori.blossom")
    id("com.github.johnrengelman.shadow")
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
            .forEach { project -> api(project) }
    }

    eternalShadowCompiler.configureBukkit?.execute(bukkit)

    tasks.withType<ShadowJar> {
        eternalShadowCompiler.configureShadowJar?.execute(this)
    }
}

fun applyExtensionShadow(eternalShadow: EternalShadowExtension) {
    blossom {
        val dependencyConstants = "src/main/java/com/eternalcode/core/loader/EternalCoreLoaderConstants.java"

        val rawDependencies =  eternalShadow.libraries.joinToString("|")
            .replace(".", "{}")
        val rawRepositories = repositories
            .filterIsInstance(MavenArtifactRepository::class.java)
            .filter { it.url.toString().startsWith("https://") }
            .map { it.url }
            .joinToString("|")
        val rawRelocations = eternalShadow.relocations
            .joinToString("|")
            .replace(".", "{}")

        replaceToken("{eternalcore-dependencies}", rawDependencies, dependencyConstants)
        replaceToken("{eternalcore-repositories}", rawRepositories, dependencyConstants)
        replaceToken("{eternalcore-relocations}", rawRelocations, dependencyConstants)
    }

    tasks.withType<ShadowJar> {
        eternalShadow.relocations.forEach { pack ->
            relocate(pack, "com.eternalcode.core.libs.$pack")
        }
    }

}

