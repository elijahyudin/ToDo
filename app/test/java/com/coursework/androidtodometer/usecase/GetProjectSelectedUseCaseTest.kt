package com.coursework.androidtodometer.usecase

import com.coursework.androidtodometer.model.Project
import com.coursework.androidtodometer.preferences.UserPreferencesRepository
import com.coursework.androidtodometer.repository.ProjectRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetProjectSelectedUseCaseTest {

    @MockK
    private val userPreferencesRepository = mockk<UserPreferencesRepository>()

    @MockK
    private val projectRepository = mockk<ProjectRepository>()

    private val getProjectSelectedUseCase =
        GetProjectSelectedUseCase(userPreferencesRepository, projectRepository)

    @Test
    fun testGetProjectSelectedUseCase() = runBlocking {
        val project = Project(1, "Name", "Description")

        coEvery { userPreferencesRepository.projectSelected() } returns flow {
            emit(1)
        }
        coEvery { projectRepository.getProject(1) } returns flow {
            emit(project)
        }

        assertEquals(project, getProjectSelectedUseCase().firstOrNull())
    }
}
