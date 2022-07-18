package com.michaelpessoni.lembrei.data.source

import androidx.lifecycle.LiveData
import com.michaelpessoni.lembrei.data.Reminder
import com.michaelpessoni.lembrei.data.source.local.RemindersDao
import javax.inject.Inject

class DefaultRemindersRepository @Inject constructor(val remindersDao: RemindersDao) : RemindersRepository {
    override suspend fun insert(reminder: Reminder) {
        remindersDao.insert(reminder)
    }

    override suspend fun update(reminder: Reminder) {
        remindersDao.update(reminder)
    }

    override suspend fun updateReminderCompletedStatus(reminderId: Long, completed: Boolean) {
        remindersDao.updateReminderCompletedStatus(reminderId, completed)
    }

    override suspend fun get(id: Long): Reminder? {
        return remindersDao.get(id)
    }

    override suspend fun deleteById(id: Long) {
        remindersDao.deleteById(id)
    }

    override suspend fun deleteAll() {
        remindersDao.deleteAll()
    }

    override suspend fun deleteCompletedReminders() {
        remindersDao.deleteCompletedReminders()
    }

    override fun getAllReminders(): List<Reminder> {
        return remindersDao.getAllReminders()
    }

    override fun observeReminders(): LiveData<List<Reminder>> {
        return remindersDao.observeReminders()
    }

    override suspend fun getNewReminder(): Reminder? {
        return remindersDao.getNewReminder()
    }
}