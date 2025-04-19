import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    alias(libs.plugins.multiplatform).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlinx.serialization.plugin)
    alias(libs.plugins.native.cocoapods)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kover)
}

dependencies {
    kover(project(":basic-ads"))
}

buildscript {
    dependencies {
        classpath(libs.dokka.base)
    }
}


allprojects {
    group = "app.lexilabs.basic"
    version = "0.2.6-beta05"

    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    extensions.configure<PublishingExtension> {
        repositories {
            maven {
                name = "maven"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2")
                credentials {
                    username = gradleLocalProperties(rootDir, providers).getProperty("sonatypeUsername")
                    password = gradleLocalProperties(rootDir, providers).getProperty("sonatypePassword")
                }
            }
        }

        val javadocJar = tasks.register<Jar>("javadocJar") {
            dependsOn(tasks.dokkaHtml)
            archiveClassifier.set("javadoc")
            from("${layout.buildDirectory}/dokka")
        }

        /** dokka generation **/
        tasks.register<Delete>("clearDokkaHtml") {
            delete("${projectDir.parent}/docs")
        }
        tasks.withType<DokkaTask>().configureEach{
            pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                dependsOn("clearDokkaHtml")
                outputDirectory = file("${projectDir.parent}/docs")
                moduleName = project.name
                moduleVersion = project.version.toString()
                customAssets = listOf(file("${projectDir.parent}/images/logo-icon.svg"))
                // Need to create a cool looking theme at some point
                //customStyleSheets = listOf(file("${projectDir.parent}/dokka/styles.css"))
                footerMessage = "(c) 2025 LexiLabs"
                failOnWarning = false
                suppressObviousFunctions = true
                suppressInheritedMembers = false
                offlineMode = false
            }
        }

        publications {
            withType<MavenPublication> {
                artifact(javadocJar)

                pom {
                    name.set("Basic")
                    description.set("Easily integrate Google Ads (AdMob) into your Kotlin Multiplatform Mobile (KMP / KMM) project")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://raw.githubusercontent.com/LexiLabs-App/basic-ads/refs/heads/main/LICENSE")
                        }
                    }
                    url.set("https://github.com/LexiLabs-App/basic-ads")
                    issueManagement {
                        system.set("Github")
                        url.set("https://github.com/LexiLabs-App/basic-ads/issues")
                    }
                    scm {
                        connection.set("https://github.com/LexiLabs-App/basic-ads.git")
                        url.set("https://github.com/LexiLabs-App/basic-ads")
                    }
                    developers {
                        developer {
                            id.set("rjamison")
                            name.set("Robert Jamison")
                            email.set("rjamison@lexilabs.app")
                            url.set("https://ads.basic.lexilabs.app")
                        }
                    }
                }
            }
        }
    }

    val publishing = extensions.getByType<PublishingExtension>()

    if (gradle.startParameter.taskNames.any { it == "publish" }) {
        extensions.configure<SigningExtension> {
            useInMemoryPgpKeys(
                gradleLocalProperties(rootDir, providers).getProperty("gpgKeyId"),
                gradleLocalProperties(rootDir, providers).getProperty("gpgKeySecret"),
                gradleLocalProperties(rootDir, providers).getProperty("gpgKeyPassword")
            )
            sign(publishing.publications)
        }
    } else {
        extensions.configure<SigningExtension> {
            useGpgCmd()
            sign(publishing.publications)
        }
    }

    // remove after https://youtrack.jetbrains.com/issue/KT-46466 is fixed
    project.tasks.withType(AbstractPublishToMaven::class.java).configureEach {
        dependsOn(project.tasks.withType(Sign::class.java))
    }
}
