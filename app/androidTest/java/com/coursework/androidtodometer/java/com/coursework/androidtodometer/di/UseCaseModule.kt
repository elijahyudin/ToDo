package com.coursework.androidtodometer.di

import com.coursework.androidtodometer.preferences.UserPreferencesRepository
import com.coursework.androidtodometer.repository.ProjectRepository
import com.coursework.androidtodometer.repository.TaskRepository
import com.coursework.androidtodometer.usecase.DeleteProjectUseCase
import com.coursework.androidtodometer.usecase.DeleteTaskUseCase
import com.coursework.androidtodometer.usecase.GetAppThemeUseCase
import com.coursework.androidtodometer.usecase.GetProjectSelectedIdUseCase
import com.coursework.androidtodometer.usecase.GetProjectSelectedUseCase
import com.coursework.androidtodometer.usecase.GetProjectsUseCase
import com.coursework.androidtodometer.usecase.GetTaskUseCase
import com.coursework.androidtodometer.usecase.InsertProjectUseCase
import com.coursework.androidtodometer.usecase.InsertTaskUseCase
import com.coursework.androidtodometer.usecase.SetAppThemeUseCase
import com.coursework.androidtodometer.usecase.SetProjectSelectedUseCase
import com.coursework.androidtodometer.usecase.SetTaskDoingUseCase
import com.coursework.androidtodometer.usecase.SetTaskDoneUseCase
import com.coursework.androidtodometer.usecase.UpdateProjectUseCase
import com.coursework.androidtodometer.usecase.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetProjectsUseCase(
        projectRepository: ProjectRepository
    ) = GetProjectsUseCase(projectRepository)

    @Provides
    @ViewModelScoped
    fun provideGetTaskUseCase(
        taskRepository: TaskRepository
    ) = GetTaskUseCase(taskRepository)

    @Provides
    @ViewModelScoped
    fun provideInsertTaskUseCase(
        userPreferencesRepository: UserPreferencesRepository,
        taskRepository: TaskRepository
    ) = InsertTaskUseCase(userPreferencesRepository, taskRepository)

    @Provides
    @ViewModelScoped
    fun provideDeleteTaskUseCase(
        taskRepository: TaskRepository
    ) = DeleteTaskUseCase(taskRepository)

    @Provides
    @ViewModelScoped
    fun provideSetTaskDoingUseCase(
        taskRepository: TaskRepository
    ) = SetTaskDoingUseCase(taskRepository)

    @Provides
    @ViewModelScoped
    fun provideSetTaskDoneUseCase(
        taskRepository: TaskRepository
    ) = SetTaskDoneUseCase(taskRepository)

    @Provides
    @ViewModelScoped
    fun provideUpdateTaskUseCase(
        taskRepository: TaskRepository
    ) = UpdateTaskUseCase(taskRepository)

    @Provides
    @ViewModelScoped
    fun provideSetProjectSelectedUseCase(
        userPreferencesRepository: UserPreferencesRepository
    ) = SetProjectSelectedUseCase(userPreferencesRepository)

    @Provides
    @ViewModelScoped
    fun provideGetProjectIdSelectedUseCase(
        userPreferencesRepository: UserPreferencesRepository
    ) = GetProjectSelectedIdUseCase(userPreferencesRepository)

    @Provides
    @ViewModelScoped
    fun provideGetProjectSelectedUseCase(
        userPreferencesRepository: UserPreferencesRepository,
        projectRepository: ProjectRepository
    ) = GetProjectSelectedUseCase(userPreferencesRepository, projectRepository)

    @Provides
    @ViewModelScoped
    fun provideInsertProjectUseCase(
        userPreferencesRepository: UserPreferencesRepository,
        projectRepository: ProjectRepository
    ) = InsertProjectUseCase(userPreferencesRepository, projectRepository)

    @Provides
    @ViewModelScoped
    fun provideDeleteProjectUseCase(
        userPreferencesRepository: UserPreferencesRepository,
        projectRepository: ProjectRepository
    ) = DeleteProjectUseCase(userPreferencesRepository, projectRepository)

    @Provides
    @ViewModelScoped
    fun provideUpdateProjectUseCase(
        projectRepository: ProjectRepository
    ) = UpdateProjectUseCase(projectRepository)

    @Provides
    @ViewModelScoped
    fun provideGetAppThemeUseCase(
        userPreferencesRepository: UserPreferencesRepository
    ) = GetAppThemeUseCase(userPreferencesRepository)

    @Provides
    @ViewModelScoped
    fun provideSetAppThemeUseCase(
        userPreferencesRepository: UserPreferencesRepository
    ) = SetAppThemeUseCase(userPreferencesRepository)
}
