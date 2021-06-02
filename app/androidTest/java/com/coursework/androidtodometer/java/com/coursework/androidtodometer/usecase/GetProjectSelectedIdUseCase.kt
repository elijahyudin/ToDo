package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetProjectSelectedIdUseCase(
    userPreferencesRepository: UserPreferencesRepository
) {

    val projectSelectedId: Flow<Int> = userPreferencesRepository.projectSelected()
}
