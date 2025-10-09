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
    dokka {
        moduleName.set(project.name)
        moduleVersion.set(project.version.toString())
        dokkaPublications.html {
            outputDirectory.set(rootDir.resolve("docs"))
            suppressObviousFunctions.set(true)
            suppressInheritedMembers.set(false)
            failOnWarning.set(false)
            offlineMode.set(false)
        }
        pluginsConfiguration.html {
            customAssets.from(rootDir.resolve("images/logo-icon.svg"))
            footerMessage.set("(c) 2025 LexiLabs")
        }
    }

    extensions.configure<MavenPublishBaseExtension> {

        mavenPublishing {
            publishToMavenCentral(automaticRelease = true)

//            signAllPublications()
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
