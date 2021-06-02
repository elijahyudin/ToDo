package com.coursework.androidtodometer.util

import com.coursework.androidtodometer.db.entity.ProjectEntity
import com.coursework.androidtodometer.db.entity.TaskEntity
import com.coursework.androidtodometer.model.Tag
import com.coursework.androidtodometer.model.TaskState

object TestUtil {

    fun createProject() = ProjectEntity(1, "Project", "Description")

    fun createTask() = TaskEntity(1, "Task", "Description", TaskState.DOING, 1, Tag.OTHER)
}
