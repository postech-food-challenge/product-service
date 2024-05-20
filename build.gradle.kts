val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project
val exposed_version: String by project
val hikaricp_version: String by project
val postgresql_version: String by project
val mockkVersion: String by project

plugins {
    kotlin("jvm") version "1.9.24"
    id("io.ktor.plugin") version "2.3.11"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.24"
    id("org.sonarqube") version "4.4.1.3373"
    id("jacoco")
}

group = "br.com.fiap.postech"
version = "0.0.1"

application {
    mainClass.set("br.com.fiap.postech.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

sonar {
    properties {
        property("sonar.projectKey", "postech-food-challenge_products-ms")
        property("sonar.organization", "postech-food-challenge")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-swagger:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.postgresql:postgresql:$postgresql_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("com.zaxxer:HikariCP:$hikaricp_version")

    testImplementation("io.mockk:mockk:${mockkVersion}")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}

jacoco {
    toolVersion = "0.8.12"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }

    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude(
                    "br/com/fiap/postech/domain/**",
                    "br/com/fiap/postech/configuration/**",
                    "br/com/fiap/postech/infraestucture/**"
                )
            }
        })
    )
}
