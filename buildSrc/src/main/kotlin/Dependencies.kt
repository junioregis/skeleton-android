object Plugins {
    const val android = "com.android.tools.build:gradle:${Versions.Build.tools}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin}"
    const val navigation = "android.arch.navigation:navigation-safe-args-gradle-plugin:${Versions.Build.navigation}"
}

object Deps {
    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"
    const val logger = "com.orhanobut:logger:${Versions.logger}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val material = "com.google.android.material:material:${Versions.material}"

    object Kotlin {
        const val main = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
        const val poet = "com.squareup:kotlinpoet:${Versions.kotlinPoet}"

        object Coroutines {
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        }
    }

    object Koin {
        const val core = "org.koin:koin-core:${Versions.koin}"
        const val android = "org.koin:koin-android:${Versions.koin}"
        const val scope = "org.koin:koin-androidx-scope:${Versions.koin}"
        const val viewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    }

    object Support {
        const val compat = "androidx.appcompat:appcompat:${Versions.Support.compat}"
        const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.Support.constraint}"
        const val card = "androidx.cardview:cardview:${Versions.Support.card}"
        const val recycler = "androidx.recyclerview:recyclerview:${Versions.Support.recycler}"

        object Navigation {
            const val fragment = "androidx.navigation:navigation-fragment:${Versions.Support.navigation}"
            const val ui = "androidx.navigation:navigation-ui:${Versions.Support.navigation}"
        }

        object Room {
            const val runtime = "androidx.room:room-runtime:${Versions.room}"
            const val compiler = "androidx.room:room-compiler:${Versions.room}"
        }
    }

    object OkHttp {
        const val core = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    }

    object Google {
        const val auth = "com.google.android.gms:play-services-auth:${Versions.Google.auth}"
        const val autoService = "com.google.auto.service:auto-service:${Versions.Google.autoService}"
    }

    object Facebook {
        const val login = "com.facebook.android:facebook-login:${Versions.Facebook.login}"
    }

    object Glide {
        const val core = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.Test.junit}"
        const val runner = "androidx.test:runner:${Versions.Test.runner}"
        const val expresso = "androidx.test.espresso:espresso-core:${Versions.Test.expresso}"
    }
}