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

dependencies {
    // Jars
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    api(Deps.Kotlin.main)
    api(Deps.Kotlin.reflect)
    api(Deps.Kotlin.Coroutines.core)
    api(Deps.Kotlin.Coroutines.android)

    // Multidex
    api(Deps.multidex)

    // Support
    api(Deps.Support.constraint)
    api(Deps.Support.compat)
    api(Deps.Support.card)
    api(Deps.Support.recycler)

    // Material
    api(Deps.material)

    // Logger
    api(Deps.logger)

    // Koin
    api(Deps.Koin.core)
    api(Deps.Koin.android)
    api(Deps.Koin.scope)
    api(Deps.Koin.viewModel)

    // Room
    api(Deps.Support.Room.runtime)
    kapt(Deps.Support.Room.compiler)

    // OkHttp
    api(Deps.OkHttp.core)
    api(Deps.OkHttp.logging)

    // Retrofit
    api(Deps.Retrofit.core)
    api(Deps.Retrofit.gson)

    // Gson
    api(Deps.gson)

    // Google
    api(Deps.Google.auth)

    // Facebook
    api(Deps.Facebook.login)

    // Glide
    api(Deps.Glide.core)
    kapt(Deps.Glide.compiler)

    // Navigation
    api(Deps.Support.Navigation.fragment)
    api(Deps.Support.Navigation.ui)

    // Unit Tests
    testImplementation(Deps.Test.junit)

    // Instrumentation Tests
    androidTestImplementation(Deps.Test.runner)
    androidTestImplementation(Deps.Test.expresso)
}
