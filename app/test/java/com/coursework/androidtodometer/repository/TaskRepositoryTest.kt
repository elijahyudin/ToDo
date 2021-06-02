package com.coursework.androidtodometer.repository

import com.coursework.androidtodometer.db.dao.TaskDao
import com.coursework.androidtodometer.db.entity.TaskEntity
import com.coursework.androidtodometer.mapper.TaskMapper.toDomain
import com.coursework.androidtodometer.model.Tag
import com.coursework.androidtodometer.model.TaskState
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class TaskRepositoryTest {

    @MockK
    private val taskDao = mockk<TaskDao>()

    private val taskRepository = TaskRepository(taskDao)

    @Test
    fun testGetTask() = runBlocking {
        val taskEntity = TaskEntity(1, "", "", TaskState.DOING, 1, Tag.OTHER)

        coEvery { taskDao.getTask(1) } returns flow {
            emit(taskEntity)
        }

        assertEquals(taskEntity.toDomain(), taskRepository.getTask(1).firstOrNull())
    }
}
