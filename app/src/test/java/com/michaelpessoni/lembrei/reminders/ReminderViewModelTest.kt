package com.michaelpessoni.lembrei.reminders

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.michaelpessoni.lembrei.R
import com.michaelpessoni.lembrei.StubDataSource
import com.michaelpessoni.lembrei.data.Reminder
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReminderViewModelTest{

    private lateinit var stubDataSource: StubDataSource
    private lateinit var reminderViewModel: ReminderViewModel

    @Before
    fun setupViewModel() {
        stubDataSource = StubDataSource()
        reminderViewModel = ReminderViewModel(stubDataSource, ApplicationProvider.getApplicationContext())
    }

    @Test
    fun addNewReminder_setsNewReminderEvent() {
        reminderViewModel.addNewReminder()

        val eventValue = reminderViewModel.newReminderEvent.getOrAwaitValue()

        assertThat(eventValue.getContentIfNotHandled(), `is`(-1L))
    }

    @Test
    fun openReminder_setsOpenReminderEvent() {
        val reminder = Reminder("title", "description")

        reminderViewModel.openReminder(reminder.reminderId)

        val eventValue = reminderViewModel.openReminderEvent.getOrAwaitValue()

        assertThat(eventValue.getContentIfNotHandled(), `is`(reminder.reminderId))
    }

    @Test
    fun clearCompletedReminders_setsSnackBarTextEvent() {
        reminderViewModel.clearCompletedReminders()

        val eventValue = reminderViewModel.snackbarText.getOrAwaitValue()

        assertThat(eventValue.getContentIfNotHandled(), `is`(R.string.delete_completed_message))
    }

    @Test
    fun clearAllReminders_setsSnackBarTextEvent() {
        reminderViewModel.clearAllReminders()

        val eventValue = reminderViewModel.snackbarText.getOrAwaitValue()

        assertThat(eventValue.getContentIfNotHandled(), `is`(R.string.clear_all_message))
    }




}