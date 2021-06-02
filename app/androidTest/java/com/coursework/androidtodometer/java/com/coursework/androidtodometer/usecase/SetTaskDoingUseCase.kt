package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.model.TaskState
import com.coursework.androidtodometer.repository.TaskRepository

class SetTaskDoingUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(id: Int) = taskRepository.setTaskDoing(id)
}
