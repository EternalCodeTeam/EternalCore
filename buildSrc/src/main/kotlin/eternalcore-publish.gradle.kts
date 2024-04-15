plugins {
    `java-library`
    `maven-publish`
}

group = "com.eternalcode"
version = "1.2.0"

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    repositories {
        mavenLocal()

        maven {
            url = uri("https://repo.eternalcode.pl/releases")

            if (version.toString().endsWith("-SNAPSHOT")) {
                url = uri("https://repo.eternalcode.pl/snapshots")
            }

            credentials {
                username = System.getenv("ETERNAL_CODE_MAVEN_USERNAME")
                password = System.getenv("ETERNAL_CODE_MAVEN_PASSWORD")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
