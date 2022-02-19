package com.michaelpessoni.lembrei.addeditreminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michaelpessoni.lembrei.data.local.RemindersDAO

class AddEditViewModelFactory(
    private val dataSource: RemindersDAO
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditViewModel::class.java)){
            return AddEditViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}