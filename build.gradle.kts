
plugins {
    kotlin("jvm")
    `jvm-test-suite`
    `maven-publish`
}

apply(from = "./repository.gradle.kts")

group = "cinira"

repositories {
    val ciniraArtifacts: Action<RepositoryHandler> by rootProject.extra
    mavenCentral()
    ciniraArtifacts(this)
}

dependencies {
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    jvmToolchain {
        target {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        val ciniraArtifacts: Action<RepositoryHandler> by rootProject.extra
        ciniraArtifacts(this)
    }
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
            dependencies {
                implementation("org.assertj:assertj-core:${project.ext["assertj_version"]}")
                runtimeOnly("ch.qos.logback:logback-classic:${project.ext["logback_version"]}")
            }
        }
    }
}

/* troubleshooting 4 */