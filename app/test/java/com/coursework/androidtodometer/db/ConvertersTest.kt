package com.coursework.androidtodometer.db

import com.coursework.androidtodometer.model.Tag
import com.coursework.androidtodometer.model.TaskState
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    @Test
    fun `Tag to String`() {
        assertEquals(Tag.ARCH.name, Converters().toString(Tag.ARCH))
    }

    @Test
    fun `String to Tag`() {
        assertEquals(Tag.ARCH, Converters().toTag(Tag.ARCH.name))
    }

    @Test
    fun `String null returns default OTHER Tag`() {
        assertEquals(Tag.OTHER, Converters().toTag(null))
    }

    @Test
    fun `TaskState to String`() {
        assertEquals(TaskState.DONE.name, Converters().toString(TaskState.DONE))
    }

    @Test
    fun `String to TaskState`() {
        assertEquals(TaskState.DOING, Converters().toTaskState(TaskState.DOING.name))
    }

    @Test
    fun `String null returns default TaskState DOING`() {
        assertEquals(TaskState.DOING, Converters().toTaskState(null))
    }
}
