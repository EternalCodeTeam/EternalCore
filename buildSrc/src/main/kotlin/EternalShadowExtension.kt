open class EternalShadowExtension {

    internal val libraries: MutableList<String> = mutableListOf()
    internal val compileOnlyDependencies: MutableList<String> = mutableListOf()
    internal val relocations: MutableList<String> = mutableListOf()

    fun library(dependency: String) {
        this.libraries.add(dependency)
        this.compileOnlyDependencies.add(dependency)
    }

    fun libraryRelocate(vararg relocations: String) {
        this.relocations.addAll(relocations)
    }

}