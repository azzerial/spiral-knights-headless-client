rootProject.name = "spiral-knights-headless-client"

dependencyResolutionManagement {
    versionCatalogs {
        create("libraries") {
            // :
            library("annotations", "org.jetbrains", "annotations").version("26.0.2")
            library("slf4j", "org.slf4j", "slf4j-api").version("2.0.17")
            // :legacy
            library("guava", "com.google.guava", "guava").version("18.0")
            library("samskivert", "com.samskivert", "samskivert").version("1.9")
            library("guice", "com.google.inject", "guice").version("3.0")
            library("ehcache", "net.sf.ehcache", "ehcache").version("1.6.0")
            library("ant", "org.apache.ant", "ant").version("1.10.11")
            // :playground
            library("logback", "ch.qos.logback", "logback-classic").version("1.3.15")
        }
    }
}

include("core")
include("legacy")
include("playground")
