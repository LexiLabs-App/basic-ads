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

/** REMEDIATION **/
dependencies {
    /** This patches all transitive dependencies that have vulnerabilities **/
    constraints {
        androidMainImplementation(libs.remediate.okhttp) {
            version { strictly(libs.versions.remediate.okhttp.get()) }
            because("CVE-2021-0341")
        }
        androidMainImplementation(libs.remediate.bitbucket) {
            version { strictly(libs.versions.remediate.bitbucket.get())}
            because("CVE-2024-29371")
        }
        androidMainImplementation(libs.remediate.netty.codec.http){
            version { strictly(libs.versions.remediate.netty.get())}
            because("CVE-2025-67735")
        }
        androidMainImplementation(libs.remediate.netty.codec.http2){
            version { strictly(libs.versions.remediate.netty.get())}
            because("CVE-2025-55163")
        }
        androidMainImplementation(libs.remediate.google.protobuf.kotlin){
            version { strictly(libs.versions.remediate.google.protobuf.get())}
            because("CVE-2024-7254")
        }
        androidMainImplementation(libs.remediate.google.protobuf.java){
            version { strictly(libs.versions.remediate.google.protobuf.get())}
            because("CVE-2024-7254")
        }
        androidMainImplementation(libs.remediate.jdom){
            version { strictly(libs.versions.remediate.jdom.get())}
            because("CVE-2021-33813")
        }
        androidMainImplementation(libs.remediate.apache.compress){
            version { strictly(libs.versions.remediate.apache.compress.get())}
            because("CVE-2024-26308")
        }
    }
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