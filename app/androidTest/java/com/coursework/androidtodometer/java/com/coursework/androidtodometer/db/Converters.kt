package com.coursework.androidtodometer.db

import androidx.room.TypeConverter
import com.coursework.androidtodometer.model.Tag
import com.coursework.androidtodometer.model.TaskState

// NOTE: each conversion must have two functions to convert A to B and B to A
// i.e. Tag to String and String to Tag
class Converters {
    @TypeConverter
    fun toString(tag: Tag?): String? = tag?.name

    @TypeConverter
    fun toTag(name: String?): Tag = name?.let { enumValueOf<Tag>(it) } ?: Tag.OTHER

    @TypeConverter
    fun toString(taskState: TaskState?): String? = taskState?.name

    @TypeConverter
    fun toTaskState(name: String?): TaskState =
        name?.let { enumValueOf<TaskState>(it) } ?: TaskState.DOING
}
