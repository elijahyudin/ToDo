package com.coursework.androidtodometer.repository

import com.coursework.androidtodometer.db.dao.ProjectDao
import com.coursework.androidtodometer.db.entity.ProjectEntity
import com.coursework.androidtodometer.db.entity.ProjectTasksRelation
import com.coursework.androidtodometer.mapper.ProjectMapper.toDomain
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ProjectRepositoryTest {

    @MockK
    private val projectDao = mockk<ProjectDao>()

    private val projectRepository = ProjectRepository(projectDao)

    @Test
    fun testGetProjects() = runBlocking {
        val projects = listOf(ProjectEntity(1, "Name", "Description"))

        coEvery { projectDao.getProjects() } returns flow {
            emit(projects)
        }

        assertEquals(projects.map { it.toDomain() }, projectRepository.getProjects().firstOrNull())
    }

    @Test
    fun testGetProject() = runBlocking {
        val project = ProjectTasksRelation(ProjectEntity(1, "Name", "Description"), emptyList())

        coEvery { projectDao.getProject(1) } returns flow {
            emit(project)
        }

        assertEquals(project.toDomain(), projectRepository.getProject(1).firstOrNull())
    }
}
