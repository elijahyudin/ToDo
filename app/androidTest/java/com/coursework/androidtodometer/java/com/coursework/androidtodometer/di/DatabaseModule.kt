package com.coursework.androidtodometer.di

import android.app.Application
import androidx.room.Room
import com.coursework.androidtodometer.db.TodometerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideTodometerDatabase(application: Application) =
        Room.databaseBuilder(application, TodometerDatabase::class.java, "TodometerDatabase.db")
            .createFromAsset("database/AppDatabase.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideProjectDao(todometerDatabase: TodometerDatabase) = todometerDatabase.projectDao()

    @Provides
    fun provideTaskDao(todometerDatabase: TodometerDatabase) = todometerDatabase.taskDao()

    @Provides
    fun provideProjectTaskViewDao(todometerDatabase: TodometerDatabase) =
        todometerDatabase.projectTaskViewDao()
}
