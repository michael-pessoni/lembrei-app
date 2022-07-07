package com.michaelpessoni.lembrei.data.source

import androidx.lifecycle.LiveData
import com.michaelpessoni.lembrei.data.Reminder

interface RemindersRepository {

    suspend fun insert(reminder: Reminder)

    suspend fun update(reminder: Reminder)

    suspend fun updateReminderCompletedStatus(reminderId: Long, completed: Boolean)

    suspend fun get(id: Long) : Reminder?

    suspend fun deleteById(id: Long)

    suspend fun deleteAll()

    suspend fun deleteCompletedReminders()

    fun getAllReminders() : List<Reminder>

    fun observeReminders() : LiveData<List<Reminder>>

    suspend fun getNewReminder() : Reminder?
}