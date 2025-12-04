plugins {
    id("java-library")
    id("com.google.protobuf") version "0.9.4"
    id("io.spring.dependency-management") version "1.1.5"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.0")
    }
}

description = "Greeting Service - API module (Protobuf events)"

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.1"
    }
    generateProtoTasks {
        ofSourceSet("main")
    }
}

dependencies {
    api("com.google.protobuf:protobuf-java:3.25.1")
}
