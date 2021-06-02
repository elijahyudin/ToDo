package com.coursework.androidtodometer.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coursework.androidtodometer.db.dao.ProjectDao
import com.coursework.androidtodometer.util.TestUtil
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProjectDaoTest : TodometerDatabaseTest() {
    private lateinit var projectDao: ProjectDao

    @Before
    fun init() {
        projectDao = todometerDatabase.projectDao()
    }

    @Test
    fun testInsertProject() = runBlocking {
        val projectA = TestUtil.createProject()
        val id = projectDao.insertProject(projectA).toInt()
        val projectB = projectDao.getProject(id).firstOrNull()?.project
        assertThat(projectB, `is`(projectA))
    }

    @Test
    fun testGetProject() = runBlocking {
        val id = projectDao.insertProject(
            TestUtil.createProject()
        ).toInt()
        val project = projectDao.getProject(id).firstOrNull()
        assertNotNull(project)
    }

    @Test
    fun testGetProjectNotExist() = runBlocking {
        val project = projectDao.getProject(10).firstOrNull()
        assertNull(project)
    }

    @Test
    fun testGetProjects() = runBlocking {
        projectDao.insertProject(
            TestUtil.createProject()
        )
        val projects = projectDao.getProjects().firstOrNull()
        assertFalse(projects.isNullOrEmpty())
    }

    @Test
    fun testUpdateProject() = runBlocking {
        val id = projectDao.insertProject(
            TestUtil.createProject()
        ).toInt()
        var project = projectDao.getProject(id).firstOrNull()
        assertEquals("Project", project?.project?.name)

        project?.project?.name = "New name"
        project?.project?.let { projectDao.updateProject(it) }

        project = projectDao.getProject(id).firstOrNull()
        assertEquals("New name", project?.project?.name)
    }

    @Test
    fun testDeleteProject() = runBlocking {
        val id = projectDao.insertProject(
            TestUtil.createProject()
        ).toInt()
        var project = projectDao.getProject(id).firstOrNull()
        assertNotNull(project)
        projectDao.deleteProject(id)
        project = projectDao.getProject(id).firstOrNull()
        assertNull(project)
    }
}
