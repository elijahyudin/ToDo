package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.model.Tag
import com.coursework.androidtodometer.model.Task
import com.coursework.androidtodometer.model.TaskState
import com.coursework.androidtodometer.preferences.UserPreferencesRepository
import com.coursework.androidtodometer.repository.TaskRepository
import kotlinx.coroutines.flow.firstOrNull

class InsertTaskUseCase(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val taskRepository: TaskRepository
) {


    suspend operator fun invoke(
        name: String,
        description: String,
        tag: Tag,
        taskState: TaskState
    ): Long? =
        userPreferencesRepository.projectSelected().firstOrNull()?.let {
            taskRepository.insert(
                Task(
                    name = name,
                    description = description,
                    projectId = it,
                    tag = tag,
                    state = taskState
                )
            )
        }
}
