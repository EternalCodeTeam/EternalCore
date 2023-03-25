rootProject.name = "EternalCore"

include(":core")
include(":paper")


project(":core").projectDir = file("core")
project(":paper").projectDir = file("paper")
include("api")
