rootProject.name = "EternalCore"

include(":eternalcore-api")
include(":eternalcore-core")
include(":eternalcore-paper")
include(":eternalcore-plugin")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
        maven { url = uri("https://repo.panda-lang.org/releases/") }
        maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
        maven { url = uri("https://repository.minecodes.pl/releases/") }
        maven { url = uri("https://repository.minecodes.pl/snapshots/") }
        maven { url = uri("https://repo.eternalcode.pl/snapshots/") }
        maven { url = uri("https://repo.eternalcode.pl/releases/") }
        maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
    }
}
include("eternalcore-docs")
