plugins {
    `java-library`
    `checkstyle`
}

group = "com.eternalcode"
version = "1.0.0"

checkstyle {
    toolVersion = "10.12.1"

    configFile = file("${rootDir}/config/checkstyle/checkstyle.xml")

    maxErrors = 0
    maxWarnings = 0
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.withType<JavaCompile>() {
    options.compilerArgs = listOf("-Xlint:deprecation", "-parameters")
    options.encoding = "UTF-8"
}
