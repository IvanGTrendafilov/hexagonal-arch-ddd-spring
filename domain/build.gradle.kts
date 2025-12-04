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

description = "Greeting Service - Domain module"

dependencies {
    implementation("org.jspecify:jspecify:0.3.0")
    implementation("org.jmolecules:jmolecules-ddd:1.9.0")
    implementation("org.springframework:spring-context")
}
