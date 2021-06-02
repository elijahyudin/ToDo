package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.model.Project
import com.coursework.androidtodometer.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow

class GetProjectsUseCase(
    val projectRepository: ProjectRepository
) {

    operator fun invoke(): Flow<List<Project?>> = projectRepository.getProjects()
}
