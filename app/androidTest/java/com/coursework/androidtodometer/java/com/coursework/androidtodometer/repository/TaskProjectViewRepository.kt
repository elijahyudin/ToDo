package com.coursework.androidtodometer.repository

import com.coursework.androidtodometer.db.dao.TaskProjectViewDao
import com.coursework.androidtodometer.db.view.TaskProjectView
import kotlinx.coroutines.flow.Flow

class TaskProjectViewRepository(private val taskProjectViewDao: TaskProjectViewDao) {

    fun getTaskProjectList(): Flow<List<TaskProjectView>> =
        taskProjectViewDao.getTaskProjectList()
}
