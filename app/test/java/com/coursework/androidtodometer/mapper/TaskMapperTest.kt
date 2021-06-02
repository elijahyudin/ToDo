package com.coursework.androidtodometer.mapper

import com.coursework.androidtodometer.db.entity.TaskEntity
import com.coursework.androidtodometer.mapper.TaskMapper.toDomain
import com.coursework.androidtodometer.mapper.TaskMapper.toEntity
import com.coursework.androidtodometer.model.Tag
import com.coursework.androidtodometer.model.Task
import com.coursework.androidtodometer.model.TaskState
import org.junit.Assert.assertEquals
import org.junit.Test

class TaskMapperTest {

    @Test
    fun `Task to TaskEntity`() {
        val task = Task(1, "Name", "Description", TaskState.DOING, 2, Tag.OTHER)
        val taskEntity = task.toEntity()
        assertEquals(task.id, taskEntity?.id)
        assertEquals(task.name, taskEntity?.name)
        assertEquals(task.description, taskEntity?.description)
        assertEquals(task.state, taskEntity?.state)
        assertEquals(task.projectId, taskEntity?.projectId)
        assertEquals(task.tag, taskEntity?.tag)
    }

    @Test
    fun `TaskEntity to Task`() {
        val taskEntity = TaskEntity(1, "Name", "Description", TaskState.DOING, 2, Tag.OTHER)
        val task = taskEntity.toDomain()
        assertEquals(taskEntity.id, task?.id)
        assertEquals(taskEntity.name, task?.name)
        assertEquals(taskEntity.description, task?.description)
        assertEquals(taskEntity.state, task?.state)
        assertEquals(taskEntity.projectId, task?.projectId)
        assertEquals(taskEntity.tag, task?.tag)
    }
}
