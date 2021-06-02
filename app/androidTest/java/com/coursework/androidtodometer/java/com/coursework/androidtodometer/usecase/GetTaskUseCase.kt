package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.model.Task
import com.coursework.androidtodometer.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTaskUseCase(
    private val taskRepository: TaskRepository
) {

    operator fun invoke(id: Int): Flow<Task?> =
        taskRepository.getTask(id)
}
