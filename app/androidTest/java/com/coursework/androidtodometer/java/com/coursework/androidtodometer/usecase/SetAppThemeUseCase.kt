package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.preferences.AppTheme
import com.coursework.androidtodometer.preferences.UserPreferencesRepository

class SetAppThemeUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend operator fun invoke(theme: Int) = userPreferencesRepository.setUserTheme(theme)
}
