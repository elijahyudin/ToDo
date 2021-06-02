package com.coursework.androidtodometer.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.coursework.androidtodometer.db.view.TaskProjectView
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskProjectViewDao {

    @Query("SELECT * FROM TaskProjectView")
    fun getTaskProjectList(): Flow<List<TaskProjectView>>
}
