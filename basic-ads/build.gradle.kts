import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.dokka)
    alias(libs.plugins.native.cocoapods)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kover)
    `maven-publish`
    signing
}

kotlin {

    // FORCES CHECK OF PUBLIC API DECLARATIONS
    // DON'T FORGET TO RUN `./gradlew apiDump`
    explicitApi()

    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
        compilerOptions { freeCompilerArgs.add("-Xwasm-attach-js-exception") }
    }

    listOf(
        iosX64(), // mobile
        iosArm64(), // mobile
        iosSimulatorArm64(), // mobile
    ).forEach {
        it.binaries.framework {
            baseName = "basic-ads"
            isStatic = true
        }
    }

    cocoapods {
        ios.deploymentTarget = libs.versions.build.ios.target.deployment.get()
        framework {
            baseName = "GoogleMobileAds"
            // Enable "isStatic", build, disable "isStatic", then build again.
//            isStatic = true
        }
        noPodspec()
        pod("Google-Mobile-Ads-SDK") {
            moduleName = "GoogleMobileAds"
            version = libs.versions.cocoapods.admob.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }

    sourceSets {
        commonMain.dependencies {
            compileOnly(libs.compose.foundation)
            api(libs.compose.foundation)
            implementation(libs.annotations)
            implementation(libs.lexilabs.basic.logging)
        }
        androidMain.dependencies {
            compileOnly(libs.google.play.services.ads)
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

    // Android JVM target target options
    androidTarget {
        publishLibraryVariants("release", "debug")
        compilations.all{
            compileTaskProvider.configure{
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }
        }
    }
}

android {
    namespace = "app.lexilabs.basic.ads"
    compileSdk = libs.versions.build.sdk.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.build.sdk.min.get().toInt()
    }
    testOptions {
        targetSdk = libs.versions.build.sdk.target.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures{
        compose = true
    }
}