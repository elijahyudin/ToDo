package com.coursework.androidtodometer.repository

import com.coursework.androidtodometer.db.dao.ProjectDao
import com.coursework.androidtodometer.mapper.ProjectMapper.toDomain
import com.coursework.androidtodometer.mapper.ProjectMapper.toEntity
import com.coursework.androidtodometer.model.Project
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProjectRepository(private val projectDao: ProjectDao) {

    fun getProjects(): Flow<List<Project?>> = projectDao.getProjects().map { list ->
        list.map { it.toDomain() }
    }

    fun getProject(id: Int): Flow<Project?> = projectDao.getProject(id).map { it.toDomain() }

    suspend fun deleteProject(id: Int) = projectDao.deleteProject(id)

    suspend fun insert(project: Project): Long? =
        project.toEntity()?.let { projectDao.insertProject(it) }

    suspend fun updateProject(project: Project) = project.toEntity()?.let {
        projectDao.updateProject(
            it
        )
    }
}
