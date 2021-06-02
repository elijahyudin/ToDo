package com.coursework.androidtodometer.util

import com.coursework.androidtodometer.model.Tag
import com.coursework.androidtodometer.model.Task
import com.coursework.androidtodometer.model.TaskState
import com.coursework.androidtodometer.util.ProgressUtil.getTasksDoneProgress
import org.junit.Assert.assertEquals
import org.junit.Test

class ProgressUtilTest {

    @Test
    fun `Test getTasksDoneProgress`() {
        val tasks = arrayListOf<Task>()
        assertEquals(0, getTasksDoneProgress(tasks))
        tasks.add(Task(1, "", "", TaskState.DONE, 2, Tag.OTHER))
        assertEquals(100, getTasksDoneProgress(tasks))
        tasks.add(Task(2, "", "", TaskState.DOING, 2, Tag.OTHER))
        assertEquals(50, getTasksDoneProgress(tasks))
        tasks.add(
            Task(2, "", "", TaskState.DOING, 2, Tag.OTHER)
        )
        assertEquals(33, getTasksDoneProgress(tasks))
    }

    @Test
    fun `getPercentage for a progress value between 0 and 100`() {
        var percentage = ProgressUtil.getPercentage(50)
        assertEquals("50%", percentage)
        percentage = ProgressUtil.getPercentage(0)
        assertEquals("0%", percentage)
        percentage = ProgressUtil.getPercentage(100)
        assertEquals("100%", percentage)
    }

    @Test
    fun `getPercentage for a progress value less than 0`() {
        val percentage = ProgressUtil.getPercentage(-1)
        assertEquals("-%", percentage)
    }

    @Test
    fun `getPercentage for a progress value greater than 100`() {
        val percentage = ProgressUtil.getPercentage(105)
        assertEquals("-%", percentage)
    }
}
