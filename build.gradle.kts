import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    with(libs.plugins) {
        alias(kotlin.multiplatform)
        alias(kotlinx.serialization)
        alias(compose)
    }
}

val jvmTargetVersion : String by properties

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = jvmTargetVersion
                freeCompilerArgs += listOf(
                    "-Xlambdas=indy"
                )
            }
        }
        testRuns.all {
            executionTask {
                useJUnitPlatform()
            }
        }
    }

    sourceSets {
        all {
            languageSettings {
                progressiveMode = true
                optIn("kotlin.contracts.ExperimentalContracts")
                optIn("kotlin.ExperimentalStdlibApi")
                optIn("kotlin.ExperimentalUnsignedTypes")
                enableLanguageFeature("ContextReceivers")
            }
        }
        jvmMain {
            dependencies {
                implementation(kone.misc.lattices)
//                implementation(kone.enumerativeCombinatorics)
                implementation("dev.lounres:ComposeLatticeCanvas:${properties["koneVersion"]}")

                implementation(libs.kotlinx.serialization.core)

                implementation("ca.gosyer:kotlin-multiplatform-appdirs:1.1.1")

//                implementation(libs.decompose)
//                implementation(libs.decompose.extensions.compose.multiplatform)

                // Compose
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.desktop.currentOs)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                api(compose.components.resources)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "dev.lounres.cuttingEdge.MainKt"

        buildTypes.release.proguard {
//            obfuscate = true
        }

        nativeDistributions {
            packageName = "CuttingEdge"
            packageVersion = version as String
            description = "CuttingEdge application for exploring cutting problem. Made for Moscow Math Fest problems committee."
            copyright = "© 2023 Gleb Minaev. All rights reserved."
            vendor = "Gleb Minaev"
            licenseFile = project.file("LICENSE")

            targetFormats(
                // Windows
                TargetFormat.Exe,
                TargetFormat.Msi,
                // Linux
//                TargetFormat.Deb,
//                TargetFormat.Rpm,
                // maxOS
//                TargetFormat.Dmg,
//                TargetFormat.Pkg
            )

            windows {
                iconFile = project.file("src/jvmMain/resources/MCCME-logo3.ico")
//                console = true
//                perUserInstall = true
//                upgradeUuid = ""
            }

            linux {
                iconFile = project.file("src/jvmMain/resources/MCCME-logo3.png")
//                rpmLicenseType = ""
            }

            macOS {
//                iconFile = project.file("")
            }
        }
    }
}