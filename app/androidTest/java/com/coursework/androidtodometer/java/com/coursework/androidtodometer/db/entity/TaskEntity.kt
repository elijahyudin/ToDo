package com.coursework.androidtodometer.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.coursework.androidtodometer.model.Tag
import com.coursework.androidtodometer.model.TaskState

@Entity(
    tableName = "Task",
    foreignKeys = [
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["project_id"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String,
    var description: String?,
    var state: TaskState,
    @ColumnInfo(name = "project_id") var projectId: Int,
    var tag: Tag?
)
