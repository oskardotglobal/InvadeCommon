plugins {
    kotlin("jvm") version "2.0.20"
}

repositories {
    mavenCentral()
}

group = "berlin.ong.student"
version = "0.0.1"

dependencies {
    implementation("io.ktor:ktor-client-android:1.6.4")
    implementation("io.ktor:ktor-client-websockets:1.6.4")
    implementation("org.jmonkeyengine:jme3-core:3.3.2-stable")
}
