package com.coursework.androidtodometer.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
abstract class TodometerDatabaseTest {
    lateinit var todometerDatabase: TodometerDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        todometerDatabase = Room.inMemoryDatabaseBuilder(
            context,
            TodometerDatabase::class.java
        ).build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        todometerDatabase.close()
    }
}
