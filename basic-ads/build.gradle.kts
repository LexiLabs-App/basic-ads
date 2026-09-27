@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalAbiValidation::class, ExperimentalBCVApi::class)

import kotlinx.validation.ExperimentalBCVApi
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.multiplatform.library)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.dokka)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kover)
}

kotlin {

    // FORCES CHECK OF PUBLIC API DECLARATIONS
    explicitApi()
    // REMEMBER TO RUN `./gradlew updateKotlinAbi` then `./gradlew checkKotlinAbi`
    abiValidation {}
    // REMEMBER TO RUN `./gradlew apiDump` then `./gradlew apiCheck`
    apiValidation {
        klib {
            enabled = true
        }
    }

    listOf(
        iosArm64(), // mobile
        iosSimulatorArm64(), // mobile
    ).forEach {
        it.binaries.framework {
            baseName = "basic-ads"
            isStatic = true
        }
    }

    swiftPMDependencies {
        iosMinimumDeploymentTarget = libs.versions.build.ios.target.deployment.get()
        swiftPackage(
            url = url("https://github.com/googleads/swift-package-manager-google-mobile-ads.git"),
            version = from(libs.versions.spm.admob.get()),
            products = listOf(product("GoogleMobileAds")),
        )
        swiftPackage(
            url = url("https://github.com/googleads/swift-package-manager-google-user-messaging-platform.git"),
            version = from(libs.versions.spm.ump.get()),
            products = listOf(product("GoogleUserMessagingPlatform"))
        )
    }

    sourceSets {
        commonMain.dependencies {
            compileOnly(libs.compose.foundation)
            api(libs.compose.foundation)
            implementation(libs.annotations)
            implementation(libs.lexilabs.basic.logging)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        androidMain.dependencies {
            compileOnly(libs.google.play.services.ads)
            compileOnly(libs.android.core)
            compileOnly(libs.android.ump)
            api(libs.android.ump)
        }
        iosMain.dependencies {}
    }

    //https://kotlinlang.org/docs/native-objc-interop.html#export-of-kdoc-comments-to-generated-objective-c-headers
    targets.withType<KotlinNativeTarget> {
        compilations["main"].compileTaskProvider.configure{
            compilerOptions {
                freeCompilerArgs.add("-Xexport-kdoc")
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    android {
        namespace = "app.lexilabs.basic.ads"
        compileSdk = libs.versions.build.sdk.compile.get().toInt()
        minSdk = libs.versions.build.sdk.min.get().toInt()
        withJava()
        withHostTest {}

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_26)
        }
    }
}