package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.model.Tag
import com.coursework.androidtodometer.model.Task
import com.coursework.androidtodometer.model.TaskState
import com.coursework.androidtodometer.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetTaskUseCaseTest {

    @MockK
    private val taskRepository = mockk<TaskRepository>()

    private val getTaskUseCase = GetTaskUseCase(taskRepository)

    @Test
    fun testGetTaskUseCase() = runBlocking {
        val task = Task(1, "Name", "Description", TaskState.DOING, 1, Tag.OTHER)

        coEvery { taskRepository.getTask(1) } returns flow {
            emit(task)
        }

        assertEquals(task, getTaskUseCase(1).firstOrNull())
    }
}
