package com.michaelpessoni.lembrei.addeditreminders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelpessoni.lembrei.Event
import com.michaelpessoni.lembrei.R
import com.michaelpessoni.lembrei.data.Reminder
import com.michaelpessoni.lembrei.data.local.RemindersDAO
import kotlinx.coroutines.launch

class AddEditViewModel(private val dataSource: RemindersDAO): ViewModel() {

    val title = MutableLiveData<String>()

    val description = MutableLiveData<String>()

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _reminderUpdatedEvent = MutableLiveData<Event<Unit>>()
    val reminderUpdatedEvent: LiveData<Event<Unit>> = _reminderUpdatedEvent

    private var reminderKey: Long? = null

    private var isNewReminder: Boolean = false

    private var reminderCompleted = false


    fun start(reminderKey: Long) {
        this.reminderKey = reminderKey

        if (reminderKey == -1L){
            isNewReminder = true
            return
        }

        isNewReminder = false

        viewModelScope.launch {
            dataSource.get(reminderKey).let { reminder ->
                if(reminder != null){
                    onReminderLoaded(reminder)
                } else{
                    onReminderNotAvailable()
                }
            }
        }
    }

    private fun onReminderNotAvailable() { 
        _snackbarText.value = Event(R.string.loading_error_message)
    }

    private fun onReminderLoaded(reminder: Reminder) {
        title.value = reminder.title
        description.value = reminder.description
        reminderCompleted = reminder.isCompleted
    }
    
    fun saveReminder() {
        val currentTitle = title.value
        val currentDescription = description.value
        
        if (currentDescription == null || currentTitle == null){
            _snackbarText.value = Event(R.string.empty_reminder_message)
            return
        }
        if (Reminder(currentTitle,currentDescription).isEmpty){
            _snackbarText.value = Event(R.string.empty_reminder_message)
            return
        }

        val currentReminderKey = reminderKey

        if (isNewReminder || currentReminderKey == null){
            createReminder(Reminder(currentTitle,currentDescription))
        } else {
            updateReminder(Reminder(
                currentTitle,
                currentDescription,
                reminderCompleted,
                currentReminderKey
            ))
        }
    }

    private fun updateReminder(reminder: Reminder) {
        if (isNewReminder) {
            throw RuntimeException("updateReminder() was called but task is new.")
        }
        viewModelScope.launch {
            dataSource.update(reminder)
            _reminderUpdatedEvent.value = Event(Unit)
        }
    }

    private fun createReminder(newReminder: Reminder) = viewModelScope.launch {
        dataSource.insert(newReminder)
        dataSource.getNewReminder()
        _reminderUpdatedEvent.value = Event(Unit)
    }

}