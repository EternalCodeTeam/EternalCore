open class EternalShadowExtension {

    internal val libraries: MutableList<String> = mutableListOf()
    internal val relocations: MutableList<String> = mutableListOf()

    internal val implementationDependencies: MutableList<Any> = mutableListOf()
    internal val compileOnlyDependencies: MutableList<String> = mutableListOf()
    internal val annotationProcessorDependencies: MutableList<String> = mutableListOf()
    internal val modules: MutableList<String> = mutableListOf()

    fun library(dependency: String) {
        this.libraries.add(dependency)
        this.compileOnlyDependencies.add(dependency)
    }

    fun libraryRuntime(dependency: Any) {
        this.implementationDependencies.add(dependency)
    }

    fun libraryRelocate(vararg relocations: String) {
        this.relocations.addAll(relocations)
    }

    fun module(module: String) {
        this.modules.add(module)
    }

    fun api(dependency: String) {
        this.compileOnlyDependencies.add(dependency)
    }

    fun useAnnotationProcessor(dependency: String) {
        this.annotationProcessorDependencies.add(dependency)
    }

}