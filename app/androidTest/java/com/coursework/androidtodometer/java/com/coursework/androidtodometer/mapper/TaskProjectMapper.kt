package com.coursework.androidtodometer.mapper

import com.coursework.androidtodometer.db.view.TaskProjectView
import com.coursework.androidtodometer.model.TaskProject

object TaskProjectMapper {

    fun TaskProjectView?.toDomain(): TaskProject? = this?.let {
        TaskProject(
            id = it.task.id,
            name = it.task.name,
            description = it.task.description,
            taskState = it.task.state,
            projectId = it.task.projectId,
            projectName = it.projectName,
            tag = it.task.tag
        )
    }
}
