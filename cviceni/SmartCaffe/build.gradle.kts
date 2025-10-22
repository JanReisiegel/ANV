plugins {
    kotlin("jvm") version "2.2.10"
    jacoco
}

group = "reisiegel.jan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect")
}

tasks.jacocoTestReport{
    reports {
        // Zajištění, že se generuje XML report, který Codecov potřebuje
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(false) // Volitelné, můžete nechat false pro úsporu místa
    }
}
tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}
kotlin {
    jvmToolchain(19)
}

