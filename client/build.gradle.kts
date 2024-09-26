plugins {
    kotlin("jvm") version "2.0.20"
}

repositories {
    mavenCentral()
}

group = "berlin.ong.student"
version = "0.0.1"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("com.ditchoom:buffer:1.4.2")
    implementation("com.ditchoom:websocket:1.1.0")
    implementation("org.jmonkeyengine:jme3-core:3.3.2-stable")
}
