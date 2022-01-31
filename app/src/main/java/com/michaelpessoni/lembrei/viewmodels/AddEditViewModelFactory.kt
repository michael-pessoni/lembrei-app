package com.michaelpessoni.lembrei.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michaelpessoni.lembrei.database.RemindersDAO

class AddEditViewModelFactory(
    private val reminderKey: Int,
    private val dataSource: RemindersDAO
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditViewModel::class.java)){
            return AddEditViewModel(reminderKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}