rootProject.name = "EternalCore"

include(":api")
include(":core")
include(":paper")


project(":core").projectDir = file("core")
project(":paper").projectDir = file("paper")
