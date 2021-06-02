package com.coursework.androidtodometer.model

data class TaskProject(
    val id: Int = 0,
    val name: String?,
    val description: String?,
    val taskState: TaskState?,
    val projectId: Int?,
    val projectName: String?,
    val tag: Tag?
)
