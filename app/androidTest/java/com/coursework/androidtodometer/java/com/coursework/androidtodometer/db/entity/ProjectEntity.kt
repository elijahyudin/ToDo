package com.coursework.androidtodometer.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Project")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String,
    var description: String
) {

    override fun toString(): String {
        return name
    }
}
