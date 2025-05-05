plugins {
    application
}

dependencies {
    implementation(libraries.annotations)
    implementation(libraries.logback)

    implementation(project(":"))
}

application {
    mainClass.set("net.azzerial.skhc.playground.Main")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val run by tasks.getting(JavaExec::class) {
    val envFile = file(".env")

    if (envFile.exists()) {
        envFile.forEachLine {
            if (it.matches(Regex("[^#][^=]+=.*"))) {
                val (key, value) = it.split("=")
                environment(key, value)
            }
        }
    }
}