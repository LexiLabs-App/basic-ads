import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.multiplatform.library)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.dokka)
    alias(libs.plugins.native.cocoapods)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kover)
}

kotlin {

    // FORCES CHECK OF PUBLIC API DECLARATIONS
    // DON'T FORGET TO RUN `./gradlew apiDump`
    explicitApi()

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
        noPodspec()
        pod("Google-Mobile-Ads-SDK") {
            moduleName = "GoogleMobileAds"
            version = libs.versions.cocoapods.admob.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
        pod("GoogleUserMessagingPlatform") {
            moduleName = "UserMessagingPlatform"
            version = libs.versions.cocoapods.ump.get()
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
            compileOnly(libs.android.core)
            compileOnly(libs.android.ump)
            api(libs.android.ump)
            /** REMEDIATION **/
            // CVE-2021-0341
            compileOnly(libs.remediate.okhttp)
            // CVE-2024-29371
            compileOnly(libs.remediate.bitbucket)
            // CVE-2025-67735
            compileOnly(libs.remediate.netty.codec.http)
            // CVE-2025-55163
            compileOnly(libs.remediate.netty.codec.http2)
            // CVE-2024-7254
            compileOnly(libs.remediate.google.protobuf.kotlin)
            // CVE-2024-7254
            compileOnly(libs.remediate.google.protobuf.java)
            // CVE-2021-33813
            compileOnly(libs.remediate.jdom)
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

    @Suppress("UnstableApiUsage")
    androidLibrary{
        namespace = "app.lexilabs.basic.ads"
        compileSdk = libs.versions.build.sdk.compile.get().toInt()
        minSdk = libs.versions.build.sdk.min.get().toInt()
        withJava()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}