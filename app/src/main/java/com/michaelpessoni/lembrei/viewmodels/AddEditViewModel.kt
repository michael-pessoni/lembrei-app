package com.michaelpessoni.lembrei.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelpessoni.lembrei.database.RemindersDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEditViewModel(private val reminderKey: Int, private val dataSource: RemindersDAO): ViewModel() {

    private val _navigateToReminderFragment = MutableLiveData<Boolean?>()

    val navigateToReminderFragment : LiveData<Boolean?>
        get() = _navigateToReminderFragment

    fun doneNavigating() {
        _navigateToReminderFragment.value = null
    }


    fun onSaveClicked(){
        _navigateToReminderFragment.value = true
    }

    fun saveReminder(title: String, description: String, date: String, priority: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {

            val reminder = dataSource.get(reminderKey) ?: return@launch

            reminder.title = title
            reminder.description = description
            reminder.date = date
            reminder.priority = priority

            dataSource.update(reminder)


        }
    }



}