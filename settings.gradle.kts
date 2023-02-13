rootProject.name = "EternalCore"

include(":plugin")
include(":core")
include(":paper")

project(":core").projectDir = file("core")
project(":paper").projectDir = file("paper")
