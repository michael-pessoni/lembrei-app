package com.michaelpessoni.lembrei.ui.reminders

import android.app.Application
import androidx.lifecycle.*
import com.michaelpessoni.lembrei.Event
import com.michaelpessoni.lembrei.R
import com.michaelpessoni.lembrei.data.Reminder
import com.michaelpessoni.lembrei.data.local.RemindersDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(private val dataSource: RemindersDao) : ViewModel() {


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
            dataSource.deleteCompletedReminders()
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
                dataSource.updateReminderCompletedStatus(reminder.reminderId, completed)

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