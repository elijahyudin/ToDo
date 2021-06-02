package com.coursework.androidtodometer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.coursework.androidtodometer.model.Project
import com.coursework.androidtodometer.model.Tag
import com.coursework.androidtodometer.model.Task
import com.coursework.androidtodometer.model.TaskState
import com.coursework.androidtodometer.usecase.DeleteProjectUseCase
import com.coursework.androidtodometer.usecase.DeleteTaskUseCase
import com.coursework.androidtodometer.usecase.GetAppThemeUseCase
import com.coursework.androidtodometer.usecase.GetProjectSelectedIdUseCase
import com.coursework.androidtodometer.usecase.GetProjectSelectedUseCase
import com.coursework.androidtodometer.usecase.GetProjectsUseCase
import com.coursework.androidtodometer.usecase.GetTaskUseCase
import com.coursework.androidtodometer.usecase.InsertProjectUseCase
import com.coursework.androidtodometer.usecase.InsertTaskUseCase
import com.coursework.androidtodometer.usecase.SetAppThemeUseCase
import com.coursework.androidtodometer.usecase.SetProjectSelectedUseCase
import com.coursework.androidtodometer.usecase.SetTaskDoingUseCase
import com.coursework.androidtodometer.usecase.SetTaskDoneUseCase
import com.coursework.androidtodometer.usecase.UpdateProjectUseCase
import com.coursework.androidtodometer.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTask: GetTaskUseCase,
    private val insertTask: InsertTaskUseCase,
    private val deleteTask: DeleteTaskUseCase,
    private val updateTask: UpdateTaskUseCase,
    private val setTaskDoing: SetTaskDoingUseCase,
    private val setTaskDone: SetTaskDoneUseCase,
    getProjects: GetProjectsUseCase,
    private val insertProject: InsertProjectUseCase,
    private val deleteProject: DeleteProjectUseCase,
    private val updateProject: UpdateProjectUseCase,
    getProjectSelected: GetProjectSelectedUseCase,
    getProjectSelectedId: GetProjectSelectedIdUseCase,
    private val setProjectSelected: SetProjectSelectedUseCase,
    getAppTheme: GetAppThemeUseCase,
    private val setAppTheme: SetAppThemeUseCase
) : ViewModel() {

    val appTheme: LiveData<Int> = getAppTheme.appTheme.asLiveData()

    val projects: LiveData<List<Project?>> = getProjects().asLiveData()

    val projectSelected: LiveData<Project?> = getProjectSelected().asLiveData()

    val projectSelectedId: LiveData<Int> = getProjectSelectedId.projectSelectedId.asLiveData()

    fun setProjectSelected(projectId: Int) = viewModelScope.launch {
        setProjectSelected.invoke(projectId)
    }

    fun getTask(id: Int): LiveData<Task?> = getTask.invoke(id).asLiveData()

    fun insertTask(name: String, description: String, tag: Tag, taskState: TaskState) =
        liveData {
            emit(insertTask.invoke(name, description, tag, taskState))
        }

    fun deleteTask(id: Int) = viewModelScope.launch {
        deleteTask.invoke(id)
    }

    fun insertProject(name: String, description: String) = liveData {
        emit(insertProject.invoke(name, description))
    }

    fun deleteProject() = viewModelScope.launch {
        deleteProject.invoke()
    }

    fun setTaskDone(id: Int) = viewModelScope.launch {
        setTaskDone.invoke(id)
    }

    fun setTaskDoing(id: Int) = viewModelScope.launch {
        setTaskDoing.invoke(id)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        updateTask.invoke(task)
    }

    fun updateProject(project: Project) = viewModelScope.launch {
        updateProject.invoke(project)
    }

    fun setAppTheme(theme: Int) = viewModelScope.launch {
        setAppTheme.invoke(theme)
    }
}
