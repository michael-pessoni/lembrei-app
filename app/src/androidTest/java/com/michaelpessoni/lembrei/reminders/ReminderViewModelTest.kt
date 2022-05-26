package com.michaelpessoni.lembrei.reminders

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.michaelpessoni.lembrei.data.Reminder
import com.michaelpessoni.lembrei.data.local.RemindersDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ReminderViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: RemindersDatabase
    private lateinit var reminderViewModel: ReminderViewModel

    @Before
    fun setupViewModel() {
        reminderViewModel = ReminderViewModel(database.remindersDatabaseDAO,
            ApplicationProvider.getApplicationContext()
        )

    }

    @Before
    fun setupDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun addNewReminder_setsNewReminderEvent(){
        reminderViewModel.addNewReminder()

        val eventValue = reminderViewModel.newReminderEvent.getOrAwaitValue()

        MatcherAssert.assertThat(
            eventValue.getContentIfNotHandled(),
            CoreMatchers.`is`(CoreMatchers.notNullValue())
        )

    }

    @Test
    fun updateReminderCompleteStatus_completeReminder() = runBlocking {
        val reminder = Reminder("title", "description")
        database.remindersDatabaseDAO.insert(reminder)

        reminderViewModel.updateReminderCompleteStatus(reminder, true)

        val returnReminder = database.remindersDatabaseDAO.get(reminder.reminderId)

        MatcherAssert.assertThat(returnReminder?.isCompleted, CoreMatchers.`is`(true))
    }

}