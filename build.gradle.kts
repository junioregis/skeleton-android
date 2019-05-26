buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(Plugins.android)
        classpath(Plugins.kotlin)
        classpath(Plugins.extensions)
        classpath(Plugins.navigation)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }

    setBuildDir("$rootDir/build/$name")
}

tasks.withType<Wrapper> {
    gradleVersion = Versions.wrapper
    distributionType = Wrapper.DistributionType.ALL
}