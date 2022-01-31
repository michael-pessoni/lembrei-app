package com.michaelpessoni.lembrei.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.michaelpessoni.lembrei.database.Reminder
import com.michaelpessoni.lembrei.database.RemindersDAO
import kotlinx.coroutines.*

class ReminderViewModel(private val dataSource: RemindersDAO, application: Application) : AndroidViewModel(application) {

    fun navigateToAddEdit(){

    }
}