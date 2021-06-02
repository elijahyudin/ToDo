package com.coursework.androidtodometer.mapper

import com.coursework.androidtodometer.db.entity.TaskEntity
import com.coursework.androidtodometer.model.Task

object TaskMapper {
    fun TaskEntity?.toDomain(): Task? = this?.let {
        Task(
            id = it.id,
            name = it.name,
            description = it.description,
            state = it.state,
            tag = it.tag,
            projectId = it.projectId
        )
    }

    fun Task?.toEntity(): TaskEntity? = this?.let {
        TaskEntity(
            id = it.id,
            name = it.name,
            description = it.description,
            state = it.state,
            tag = it.tag,
            projectId = it.projectId
        )
    }
}
