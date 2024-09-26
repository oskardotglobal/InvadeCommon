repositories {
    mavenCentral()
}

plugins {
    id("io.ktor.plugin") version "3.0.0-rc-1"
    kotlin("jvm")
}

group = "berlin.ong.student.invadeserver"
version = "0.0.1"

application {
    mainClass.set("berlin.ong.student.invadeserver.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-websockets-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")

    implementation("ch.qos.logback:logback-classic:1.4.14")
}
