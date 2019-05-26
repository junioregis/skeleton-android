/*apply {
    from("${rootProject.rootDir}/common.gradle.kts")
}*/

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Versions.AndroidSdk.compile)

    defaultConfig {
        minSdkVersion(Versions.AndroidSdk.min)
        targetSdkVersion(Versions.AndroidSdk.target)
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        getByName("main") { java.srcDirs(file("src/main/kotlin")) }
        getByName("test") { java.srcDirs(file("src/test/kotlin")) }
    }
}

kapt {
    useBuildCache = true
    correctErrorTypes = true
}

dependencies {
    implementation(project(":core"))
}
