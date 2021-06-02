package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.model.Project
import com.coursework.androidtodometer.repository.ProjectRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetProjectsUseCaseTest {

    @MockK
    private val projectRepository = mockk<ProjectRepository>()

    private val getProjectsUseCase = GetProjectsUseCase(projectRepository)

    @Test
    fun testGetProjectsUseCase() = runBlocking {
        val projects = listOf(Project(1, "Name", "Description"))

        coEvery { projectRepository.getProjects() } returns flow {
            emit(projects)
        }

        assertEquals(projects, getProjectsUseCase().firstOrNull())
    }
}
