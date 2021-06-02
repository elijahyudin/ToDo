package com.coursework.androidtodometer.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ProjectTasksRelation(
    @Embedded val project: ProjectEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "project_id"
    )
    val tasks: List<TaskEntity>
)
