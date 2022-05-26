package com.michaelpessoni.lembrei.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.michaelpessoni.lembrei.data.Reminder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RemindersDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: RemindersDatabase

    @Before
    fun setupDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertReminderAndGetById() = runBlockingTest {
        val reminder = Reminder("title", "desc", false)
        database.remindersDatabaseDAO.insert(reminder)

        val loadedReminder = database.remindersDatabaseDAO.get(reminder.reminderId)

        assertThat(loadedReminder as Reminder, notNullValue())
        assertThat(loadedReminder.isEmpty, `is`(reminder.isEmpty))
        assertThat(loadedReminder.reminderId, `is`(reminder.reminderId))
        assertThat(loadedReminder.title, `is`(reminder.title))
        assertThat(loadedReminder.description, `is`(reminder.description))
        assertThat(loadedReminder.isCompleted, `is`(reminder.isCompleted))
    }

    @Test
    fun updateReminderAndGetById() = runBlockingTest {
        val reminder = Reminder("title", "desc", false)
        database.remindersDatabaseDAO.insert(reminder)

        val updatedReminder = Reminder("newTitle", "newDescription", true, reminder.reminderId)
        database.remindersDatabaseDAO.update(updatedReminder)

        val loadedReminder = database.remindersDatabaseDAO.get(reminder.reminderId)

        assertThat(loadedReminder as Reminder, notNullValue())
        assertThat(loadedReminder.reminderId, `is`(updatedReminder.reminderId))
        assertThat(loadedReminder.title, `is`(updatedReminder.title))
        assertThat(loadedReminder.description, `is`(updatedReminder.description))
        assertThat(loadedReminder.isCompleted, `is`(updatedReminder.isCompleted))
    }

    @Test
    fun updateReminderCompletedStatus() = runBlockingTest {
        val reminder = Reminder("title", "desc", false)
        database.remindersDatabaseDAO.insert(reminder)

        database.remindersDatabaseDAO.updateReminderCompletedStatus(reminder.reminderId, true)

        val loadedReminder = database.remindersDatabaseDAO.get(reminder.reminderId)

        assertThat(loadedReminder as Reminder, notNullValue())
        assertThat(loadedReminder.isCompleted, `is`(true))
    }

    @Test
    fun deleteCompletedReminders() = runBlockingTest {
        val reminder1 = Reminder("title1", "desc1", false)
        database.remindersDatabaseDAO.insert(reminder1)
        val reminder2 = Reminder("title2", "desc2", false)
        database.remindersDatabaseDAO.insert(reminder2)
        val reminder3 = Reminder("title3", "desc3", true)
        database.remindersDatabaseDAO.insert(reminder3)
        val reminder4 = Reminder("title4", "desc4", true)
        database.remindersDatabaseDAO.insert(reminder4)


        var remindersList = database.remindersDatabaseDAO.getAllReminders()

        assertThat(remindersList.size, `is`(4))

        database.remindersDatabaseDAO.deleteCompletedReminders()

        remindersList = database.remindersDatabaseDAO.getAllReminders()

        assertThat(remindersList.size, `is`(2))
    }
}