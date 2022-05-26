package com.michaelpessoni.lembrei.reminders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.michaelpessoni.lembrei.Event
import com.michaelpessoni.lembrei.R
import com.michaelpessoni.lembrei.data.Reminder
import com.michaelpessoni.lembrei.data.local.RemindersDAO
import kotlinx.coroutines.launch

class ReminderViewModel(private val dataSource: RemindersDAO, application: Application) : AndroidViewModel(application) {


    val reminderList = dataSource.observeReminders()

    private val _newReminderEvent = MutableLiveData<Event<Long>>()
    val newReminderEvent: LiveData<Event<Long>>
        get() = _newReminderEvent
    
    private val _openReminderEvent = MutableLiveData<Event<Long>>()
    val openReminderEvent: LiveData<Event<Long>>
        get() = _openReminderEvent

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    
    fun clearCompletedReminders() {
        viewModelScope.launch { 
            dataSource.deleteCompletedTasks()
            showSnackbarMessage(R.string.delete_completed_message)
        }
    } 
    
    fun clearAllReminders() {
        viewModelScope.launch { 
            dataSource.deleteAll()
            showSnackbarMessage(R.string.clear_all_message)
        }
    }

    fun updateReminderCompleteStatus(reminder: Reminder, completed: Boolean) {
        viewModelScope.launch {
                dataSource.updateCompleted(reminder.reminderId, completed)

        }
    }

    fun addNewReminder() {
        _newReminderEvent.value = Event(-1L)
    }

    fun openReminder(reminderId: Long) {
        _openReminderEvent.value = Event(reminderId)
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }

}