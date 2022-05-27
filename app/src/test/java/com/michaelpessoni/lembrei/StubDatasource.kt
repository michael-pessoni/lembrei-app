package com.michaelpessoni.lembrei

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.michaelpessoni.lembrei.data.Reminder
import com.michaelpessoni.lembrei.data.local.RemindersDAO

class StubDataSource : RemindersDAO{
    override suspend fun insert(reminder: Reminder) {
        TODO("Not yet implemented")
    }

    override suspend fun update(reminder: Reminder) {
        TODO("Not yet implemented")
    }

    override suspend fun updateReminderCompletedStatus(reminderId: Long, completed: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun get(id: Long): Reminder? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCompletedReminders() {
        // No Op
    }

    override fun getAllReminders(): List<Reminder> {
        TODO("Not yet implemented")
    }

    override fun observeReminders(): LiveData<List<Reminder>> {
        val emptyList = emptyList<Reminder>()
        val _liveData = MutableLiveData<List<Reminder>>()
        _liveData.value = emptyList
        return _liveData
    }

    override suspend fun getNewReminder(): Reminder? {
        TODO("Not yet implemented")
    }


}