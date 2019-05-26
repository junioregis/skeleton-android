plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Versions.AndroidSdk.compile)

    defaultConfig {
        applicationId = "com.domain.skeleton"

        minSdkVersion(Versions.AndroidSdk.min)
        targetSdkVersion(Versions.AndroidSdk.target)

        versionCode = 1
        versionName = "1.0.0"

        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    dexOptions {
        preDexLibraries = true
        jumboMode = true
        javaMaxHeapSize = "4g"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        getByName("main") { java.srcDirs(file("src/main/kotlin")) }
        getByName("test") { java.srcDirs(file("src/test/kotlin")) }
    }

    lintOptions {
        isCheckReleaseBuilds = false
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            manifestPlaceholders = mapOf("usesCleartextTraffic" to "true")
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            manifestPlaceholders = mapOf("usesCleartextTraffic" to "false")
        }
    }
}

kapt {
    useBuildCache = true
    correctErrorTypes = true
}

dependencies {
    implementation(project(":core"))

    implementation(project(":features:auth"))
    implementation(project(":features:home"))
    implementation(project(":features:profile"))
}
