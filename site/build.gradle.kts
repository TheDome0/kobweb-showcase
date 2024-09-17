import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import org.komapper.codegen.PropertyTypeResolver

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.komapper)
}

group = "com.varabyte.kobwebshowcase"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")
        }
    }
}

kotlin {
    configAsKobwebApplication("kobwebshowcase" , includeServer = true)

    sourceSets {
        all {
            languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
        }

        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
        }

        jsMain.dependencies {
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            // This default template uses built-in SVG icons, but what's available is limited.
            // Uncomment the following if you want access to a large set of font-awesome icons:
            // implementation(libs.silk.icons.fa)
            implementation(libs.kobwebx.markdown)
        }

        jvmMain.dependencies {
            compileOnly(libs.kobweb.api) // Provided by Kobweb backend at runtime
            implementation(libs.r2dbc.pool)
            implementation(libs.r2dbc.spi)
            implementation(libs.postgres.r2dbc)
            implementation(libs.komapper.starter.r2dbc)
            implementation(libs.komapper.dialect.postgresql.r2dbc)
            runtimeOnly(libs.komapper.slf4j)
            implementation(libs.dotenv)

        }
    }
}

dependencies {
    add("kspJvm", platform(libs.komapper.platform))
    add("kspJvm", "org.komapper:komapper-processor")
}

komapper {
    generators {
        register("postgresql") {
            jdbc {
                driver.set("org.postgresql.Driver")
                url.set("jdbc:postgresql://localhost:5432/postgres")
                user.set(env.fetch("DB_USER"))
                password.set(env.fetch("DB_PW"))
            }
            packageName.set("com.varabyte.kobwebshowcase.db")
            destinationDir.set(File("src/jvmMain/kotlin"))
            overwriteEntities.set(true)
            overwriteDefinitions.set(true)
            val defaultResolver = propertyTypeResolver.get()
            val customResolver = PropertyTypeResolver { table, column ->
                when (column.typeName) {
                    "uuid" -> "kotlin.uuid.Uuid"
                    "timestamp" -> "kotlinx.datetime.LocalDateTime"
                    "date" -> "kotlinx.datetime.LocalDate"
                    "json" -> "io.r2dbc.postgresql.codec.Json"
                    else -> defaultResolver.resolve(table, column)
                }
            }
            propertyTypeResolver.set(customResolver)
        }
    }
}
