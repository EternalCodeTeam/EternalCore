plugins {
    `eternalcode-java`
    `eternalcore-repositories`
}

dependencies {
    // add eternalcore-core module
    implementation(project(":eternalcore-core"))
    
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("dev.rollczi.litecommands:core:2.8.7")
}
