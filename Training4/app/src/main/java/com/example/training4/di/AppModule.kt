package com.example.training4.di

import androidx.room.Room
import com.example.training4.data.repository.HabitDatabase
import com.example.training4.data.repository.HabitRepository
import com.example.training4.data.repository.HabitRepositoryImpl
import com.example.training4.model.HabitViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            HabitDatabase::class.java,
            "habit.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<HabitDatabase>().dao }

    single<HabitRepository> { HabitRepositoryImpl(get()) }
    viewModelOf(::HabitViewModel)
}
