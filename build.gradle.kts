import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask
import com.vanniktech.maven.publish.MavenPublishBaseExtension


plugins {
    alias(libs.plugins.multiplatform).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlinx.serialization.plugin)
    alias(libs.plugins.native.cocoapods)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kover)
}

dependencies { kover(project(":basic-ads")) }

buildscript {
    plugins { alias(libs.plugins.maven.publish)}
    dependencies { classpath(libs.dokka.base) }
}

allprojects {
    group = "app.lexilabs.basic"
    version = rootProject.libs.versions.ads.get()

    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "com.vanniktech.maven.publish")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

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

    extensions.configure<MavenPublishBaseExtension> {

        mavenPublishing {
            publishToMavenCentral(automaticRelease = true)

            signAllPublications()
            coordinates(group.toString(), project.name, version.toString())
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
