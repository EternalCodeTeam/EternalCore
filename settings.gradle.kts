rootProject.name = "EternalCore"

include(":eternalcore-api")
include(":eternalcore-core")
include(":eternalcore-paper")
include(":eternalcore-plugin")
include(":eternalcore-docs-api")
include(":eternalcore-docs-generate")
include(":eternalcore-api-example")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.eternalcode.pl/releases/")
    }
}