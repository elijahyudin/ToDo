package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.repository.TaskRepository

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(taskId: Int) =
        taskRepository.deleteTask(taskId)
}
