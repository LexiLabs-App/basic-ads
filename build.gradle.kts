import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.android.utils.TraceUtils.simpleId
import org.gradle.internal.impldep.org.bouncycastle.openpgp.PGPKeyRing
import org.gradle.internal.impldep.org.bouncycastle.openpgp.PGPSecretKeyRing

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

allprojects {
    group = "app.lexilabs.basic"
    version = "0.2.5"

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
            delete("${projectDir.parent}/docs/${project.name}")
        }
        tasks.dokkaHtml {
            dependsOn("clearDokkaHtml")
            outputDirectory.set(file("${projectDir.parent}/docs/${project.name}"))
            moduleName.set(project.name)
            moduleVersion.set(project.version.toString())
            failOnWarning.set(false)
            suppressObviousFunctions.set(true)
            suppressInheritedMembers.set(false)
            offlineMode.set(false)
        }

        publications {
            withType<MavenPublication> {
                artifact(javadocJar)

                pom {
                    name.set("Basic")
                    description.set("Integrate basic features across all your Kotlin Multiplatform apps with a single library")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://basic.lexilabs.app/LICENSE")
                        }
                    }
                    url.set("https://github.com/LexiLabs-App/basic")
                    issueManagement {
                        system.set("Github")
                        url.set("https://github.com/LexiLabs-App/basic/issues")
                    }
                    scm {
                        connection.set("https://github.com/LexiLabs-App/basic.git")
                        url.set("https://github.com/LexiLabs-App/basic")
                    }
                    developers {
                        developer {
                            id.set("rjamison")
                            name.set("Robert Jamison")
                            email.set("rjamison@lexilabs.app")
                        }
                    }
                }
            }
        }
    }

    val publishing = extensions.getByType<PublishingExtension>()
    extensions.configure<SigningExtension> {
        useInMemoryPgpKeys(
            gradleLocalProperties(rootDir, providers).getProperty("gpgKeyId"),
            gradleLocalProperties(rootDir, providers).getProperty("gpgKeySecret"),
            gradleLocalProperties(rootDir, providers).getProperty("gpgKeyPassword")
        )
        sign(publishing.publications)
    }

//    val publishing = extensions.getByType<PublishingExtension>()
//    extensions.configure<SigningExtension> {
//        useGpgCmd()
//        sign(publishing.publications)
//    }

    // remove after https://youtrack.jetbrains.com/issue/KT-46466 is fixed
    project.tasks.withType(AbstractPublishToMaven::class.java).configureEach {
        dependsOn(project.tasks.withType(Sign::class.java))
    }
}