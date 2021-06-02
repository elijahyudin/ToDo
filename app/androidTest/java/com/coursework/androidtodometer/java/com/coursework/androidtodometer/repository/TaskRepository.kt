package com.coursework.androidtodometer.repository

import com.coursework.androidtodometer.db.dao.TaskDao
import com.coursework.androidtodometer.mapper.TaskMapper.toDomain
import com.coursework.androidtodometer.mapper.TaskMapper.toEntity
import com.coursework.androidtodometer.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val taskDao: TaskDao) {

    fun getTask(id: Int): Flow<Task?> = taskDao.getTask(id).map { it.toDomain() }

    suspend fun deleteTask(id: Int) = taskDao.deleteTask(id)

    suspend fun insert(task: Task): Long? = task.toEntity()?.let { taskDao.insertTask(it) }

    suspend fun setTaskDone(id: Int) = taskDao.setTaskDone(id)

    suspend fun setTaskDoing(id: Int) = taskDao.setTaskDoing(id)

    suspend fun updateTask(task: Task) = task.toEntity()?.let { taskDao.updateTask(it) }
}
