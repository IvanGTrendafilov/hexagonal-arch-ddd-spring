plugins {
    id("java-library")
    id("org.springframework.boot") version "3.3.0" apply false
    id("io.spring.dependency-management") version "1.1.5"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.0")
    }
}

description = "Greeting Service - Data module"

dependencies {
    implementation(project(":domain"))
    implementation("org.jspecify:jspecify:0.3.0")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-jdbc")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
}
