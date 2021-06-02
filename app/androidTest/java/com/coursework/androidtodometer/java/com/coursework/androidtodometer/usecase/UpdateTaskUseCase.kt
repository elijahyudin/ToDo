package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.model.Task
import com.coursework.androidtodometer.repository.TaskRepository

class UpdateTaskUseCase(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(task: Task) = taskRepository.updateTask(task)
}
