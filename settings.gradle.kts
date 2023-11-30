rootProject.name = "CuttingEdge"

val projectProperties = java.util.Properties()
file("gradle.properties").inputStream().use {
    projectProperties.load(it)
}

val koneVersion: String by projectProperties

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://repo.kotlin.link")
        mavenLocal()
    }

    versionCatalogs {
        create("kone").from("dev.lounres:kone.versionCatalog:$koneVersion")
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
    }
}