package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.model.Project
import com.coursework.androidtodometer.preferences.UserPreferencesRepository
import com.coursework.androidtodometer.repository.ProjectRepository

class InsertProjectUseCase(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val projectRepository: ProjectRepository
) {


    suspend operator fun invoke(name: String, description: String): Long? =
        projectRepository.insert(Project(name = name, description = description))?.let {
            userPreferencesRepository.setProjectSelected(it.toInt())
            it
        }
}
