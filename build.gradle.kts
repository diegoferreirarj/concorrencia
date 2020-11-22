plugins {
    application
    kotlin("jvm") version "1.4.10"
}

group = "br.com.exemplos"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
}

application {
    mainClass.set("br.com.exemplos.concorrencia.AppKt")
}


