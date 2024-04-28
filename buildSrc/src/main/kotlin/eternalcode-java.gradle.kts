plugins {
    `java-library`
    checkstyle
}

group = "com.eternalcode"
version = "1.2.0"

checkstyle {
    toolVersion = "10.16.0"

    configFile = file("${rootDir}/config/checkstyle/checkstyle.xml")
    configProperties["checkstyle.suppressions.file"] = "${rootDir}/config/checkstyle/suppressions.xml"

    maxErrors = 0
    maxWarnings = 0
}

// https://github.com/JabRef/jabref/pull/10812/files#diff-49a96e7eea8a94af862798a45174e6ac43eb4f8b4bd40759b5da63ba31ec3ef7R267
configurations.named("checkstyle") {
    resolutionStrategy {
        capabilitiesResolution {
            withCapability("com.google.collections:google-collections") {
                select("com.google.guava:guava:33.1.0-jre")
            }
        }
    }
}


java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.withType<JavaCompile>() {
    options.compilerArgs = listOf("-Xlint:deprecation", "-parameters")
    options.encoding = "UTF-8"
}
