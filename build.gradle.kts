group = "cinira"

plugins {
    kotlin("jvm")
    `jvm-test-suite`
}

apply(from = "./repository.gradle.kts")

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
