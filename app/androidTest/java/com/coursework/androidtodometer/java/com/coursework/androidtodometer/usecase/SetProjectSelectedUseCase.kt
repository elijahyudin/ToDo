package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.preferences.UserPreferencesRepository

class SetProjectSelectedUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend operator fun invoke(id: Int) {
        userPreferencesRepository.setProjectSelected(id)
    }
}
