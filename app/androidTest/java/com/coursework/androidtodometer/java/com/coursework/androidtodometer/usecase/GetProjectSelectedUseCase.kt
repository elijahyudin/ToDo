package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.model.Project
import com.coursework.androidtodometer.preferences.UserPreferencesRepository
import com.coursework.androidtodometer.repository.ProjectRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class GetProjectSelectedUseCase(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val projectRepository: ProjectRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Project?> =
        userPreferencesRepository.projectSelected().flatMapLatest { projectId ->
            projectRepository.getProject(projectId)
        }
}
