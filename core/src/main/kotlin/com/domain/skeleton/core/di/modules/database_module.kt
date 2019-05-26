package com.domain.skeleton.core.di.modules

import androidx.room.Room
import com.domain.skeleton.core.datasource.local.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(),
                AppDatabase::class.java, "db")
                .build()
    }
}