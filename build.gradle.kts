import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.gradleup.shadow") version "9.3.0+"
}

group = "org.example"
version = "1.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.imgui.java.binding)
    compileOnly(libs.imgui.java.lwjgl3)

    compileOnly(libs.imgui.java.natives.windows)
    compileOnly(libs.imgui.java.natives.linux)
    compileOnly(libs.imgui.java.natives.macos)

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    compileOnly("org.lwjgl.lwjgl:lwjgl:2.9.3")
}

tasks.test {
    useJUnitPlatform()
}


tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("all")
    mergeServiceFiles()

    exclude("org.lwjgl.lwjgl:.*")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}
