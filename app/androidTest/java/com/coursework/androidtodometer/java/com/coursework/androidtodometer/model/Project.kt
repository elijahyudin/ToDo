package com.coursework.androidtodometer.model

data class Project(
    val id: Int = 0,
    val name: String,
    val description: String,
    val tasks: List<Task?> = emptyList()
)
