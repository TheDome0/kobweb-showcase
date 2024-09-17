plugins {
    alias(libs.plugins.dotenv).apply(true)
}

buildscript {
    dependencies {
        classpath("org.postgresql:postgresql:42.3.4")
    }
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        maven("https://us-central1-maven.pkg.dev/varabyte-repos/public")
    }
}