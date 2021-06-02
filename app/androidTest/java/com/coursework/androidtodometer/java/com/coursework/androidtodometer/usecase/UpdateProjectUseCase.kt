package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.model.Project
import com.coursework.androidtodometer.repository.ProjectRepository

class UpdateProjectUseCase(
    private val projectRepository: ProjectRepository
) {

    suspend operator fun invoke(project: Project) =
        projectRepository.updateProject(project)
}
