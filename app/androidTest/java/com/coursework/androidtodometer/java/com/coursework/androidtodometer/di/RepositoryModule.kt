package com.coursework.androidtodometer.di

import com.coursework.androidtodometer.db.dao.ProjectDao
import com.coursework.androidtodometer.db.dao.TaskDao
import com.coursework.androidtodometer.db.dao.TaskProjectViewDao
import com.coursework.androidtodometer.repository.ProjectRepository
import com.coursework.androidtodometer.repository.TaskProjectViewRepository
import com.coursework.androidtodometer.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao) = TaskRepository(taskDao)

    @Provides
    @Singleton
    fun provideProjectRepository(projectDao: ProjectDao) = ProjectRepository(projectDao)

    @Provides
    @Singleton
    fun provideTaskProjectViewRepository(taskProjectViewDao: TaskProjectViewDao) = TaskProjectViewRepository(taskProjectViewDao)
}
