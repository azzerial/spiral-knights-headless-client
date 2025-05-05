import java.util.*

plugins {
    `java-library`
    `maven-publish`

    id("com.github.johnrengelman.shadow") version "7.1.2"
}

// Project Configuration //

val versionInfo = Version(major = 1, minor = 0, revision = 0)
val buildDate = Date()

allprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    group = "net.azzerial"
    version = versionInfo.toString()

    val artifactId = if (rootProject == project) project.name else "${rootProject.name}-${project.name}"
    val moduleName = "$group.skhc${if (rootProject == project) "" else ".${project.name}"}"

    java {
        withSourcesJar()
    }

    tasks.jar {
        archiveBaseName.set(artifactId)
        archiveVersion.set("${project.version}")
        archiveExtension.set("jar")
        
        manifest.attributes(
                "Implementation-Title" to artifactId,
                "Implementation-Version" to project.version,
                "Automatic-Module-Name" to moduleName,
                "Built-By" to System.getProperty("user.name"),
                "Built-Date" to buildDate,
                "Built-JDK" to System.getProperty("java.version"),
                "Built-Gradle" to gradle.gradleVersion
        )
    }

    tasks.shadowJar {
        archiveClassifier.set("withDependencies")
        exclude("*.pom")
    }
}

java {
    withJavadocJar()
}

tasks.javadoc {
    options {
        memberLevel = JavadocMemberLevel.PROTECTED
        encoding = "UTF-8"
    }
}

// Dependency Configuration //

allprojects {
    repositories {
        mavenCentral()
    }
}

dependencies {
    implementation(libraries.annotations)
    implementation(libraries.slf4j)

    implementation(project(":core"))
}

// Publishing //

fun MavenPom.populate() {
    name.set("Spiral Knights Headless Client")
    description.set("Spiral Knights client without a GUI")
    url.set("https://github.com/azzerial/spiral-knights-headless-client")
    inceptionYear.set("2025")

    licenses {
        license {
            name.set("The Apache License, Version 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            distribution.set("repo")
        }
    }
    developers {
        developer {
            id.set("azzerial")
            name.set("Robin Mercier")
            email.set("robin@azzerial.net")
            url.set("https://azzerial.net")
        }
    }
}

allprojects {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["java"])

                artifactId = if (rootProject == project) project.name else "${rootProject.name}-${project.name}"
                groupId = project.group as String
                version = project.version as String

                pom.populate()
            }
        }
        repositories {
            maven {
                name = "azzerialMavenRepository"
                url = uri("https://maven.azzerial.net/${versionInfo.getBuildRepository()}")

                credentials(PasswordCredentials::class)
            }
        }
    }
}

// Helper //

data class Version(
        val major: Int,
        val minor: Int,
        val revision: Int,
        val classifier: String? = null
) {
    fun getBuildRepository(): String {
        return if (classifier != null && classifier.equals("SNAPSHOT", ignoreCase = true)) "snapshots" else "releases"
    }

    override fun toString(): String {
        return "$major.$minor.$revision" + if (classifier != null) "-$classifier" else ""
    }
}