package com.coursework.androidtodometer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coursework.androidtodometer.db.dao.ProjectDao
import com.coursework.androidtodometer.db.dao.TaskDao
import com.coursework.androidtodometer.db.dao.TaskProjectViewDao
import com.coursework.androidtodometer.db.entity.ProjectEntity
import com.coursework.androidtodometer.db.entity.TaskEntity
import com.coursework.androidtodometer.db.view.TaskProjectView

@Database(
    entities = [ProjectEntity::class, TaskEntity::class],
    views = [TaskProjectView::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TodometerDatabase : RoomDatabase() {

    abstract fun projectDao(): ProjectDao

    abstract fun taskDao(): TaskDao

    abstract fun projectTaskViewDao(): TaskProjectViewDao
}
