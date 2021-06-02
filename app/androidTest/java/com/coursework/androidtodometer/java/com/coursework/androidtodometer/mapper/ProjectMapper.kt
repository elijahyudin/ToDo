package com.coursework.androidtodometer.mapper

import com.coursework.androidtodometer.db.entity.ProjectEntity
import com.coursework.androidtodometer.db.entity.ProjectTasksRelation
import com.coursework.androidtodometer.mapper.TaskMapper.toDomain
import com.coursework.androidtodometer.model.Project

object ProjectMapper {
    fun ProjectEntity?.toDomain(): Project? = this?.let {
        Project(
            id = it.id,
            name = it.name,
            description = it.description
        )
    }

    fun ProjectTasksRelation?.toDomain(): Project? = this?.let {
        Project(
            id = it.project.id,
            name = it.project.name,
            description = it.project.description,
            tasks = it.tasks.map { task -> task.toDomain() }.sortedBy { task -> task?.state }
        )
    }

    fun Project?.toEntity(): ProjectEntity? = this?.let {
        ProjectEntity(
            id = it.id,
            name = it.name,
            description = it.description
        )
    }
}
