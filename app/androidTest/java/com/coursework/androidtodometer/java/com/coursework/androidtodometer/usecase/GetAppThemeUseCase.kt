package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.preferences.AppTheme
import com.coursework.androidtodometer.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetAppThemeUseCase(
    userPreferencesRepository: UserPreferencesRepository
) {

    val appTheme: Flow<Int> = userPreferencesRepository.getUserTheme()
}
