package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.preferences.UserPreferencesRepository
import com.coursework.androidtodometer.repository.ProjectRepository
import kotlinx.coroutines.flow.firstOrNull

class DeleteProjectUseCase(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val projectRepository: ProjectRepository
) {

    suspend operator fun invoke() {
        val projectId = userPreferencesRepository.projectSelected().firstOrNull()
        projectId?.let { projectRepository.deleteProject(it) }
        val projects = projectRepository.getProjects().firstOrNull()
        projects?.firstOrNull()?.let { project ->
            userPreferencesRepository.setProjectSelected(project.id)
        }
    }
}
