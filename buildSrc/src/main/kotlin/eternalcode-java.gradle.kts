plugins {
    `java-library`
    checkstyle
}

group = "com.eternalcode"
version = "2.0.0-SNAPSHOT"

checkstyle {
    toolVersion = "11.0.1"

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
                select("com.google.guava:guava:33.5.0-jre")
            }
        }
    }
}


java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.withType<JavaCompile>() {
    options.compilerArgs = listOf("-Xlint:deprecation", "-parameters")
    options.encoding = "UTF-8"
    options.release = 21
}
