package com.coursework.androidtodometer.util

import androidx.annotation.IntRange
import com.coursework.androidtodometer.model.Task
import com.coursework.androidtodometer.model.TaskState

object ProgressUtil {

    fun getTasksDoneProgress(list: List<Task?>): Int =
        list.takeUnless { it.isEmpty() }?.let {
            ((it.filter { task -> task?.state == TaskState.DONE }.size / it.size.toDouble()) * 100).toInt()
        } ?: 0

    fun getPercentage(@IntRange(from = 0, to = 100) progress: Int) =
        progress.takeIf { it in 0..100 }?.let { "$it%" } ?: "-%"
}
