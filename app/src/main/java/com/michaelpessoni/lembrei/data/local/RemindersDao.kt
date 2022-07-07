package com.michaelpessoni.lembrei.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.michaelpessoni.lembrei.data.Reminder

@Dao
interface RemindersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: Reminder)

    @Update
    suspend fun update(reminder: Reminder)

    @Query("UPDATE reminders_table SET completed = :completed WHERE reminderId = :reminderId")
    suspend fun updateReminderCompletedStatus(reminderId: Long, completed: Boolean)

    @Query("SELECT * FROM reminders_table WHERE reminderId = :id")
    suspend fun get(id: Long) : Reminder?

    @Query("DELETE FROM reminders_table WHERE reminderId = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM reminders_table")
    suspend fun deleteAll()

    @Query("DELETE FROM reminders_table WHERE completed = 1")
    suspend fun deleteCompletedReminders()

    @Query("SELECT * FROM reminders_table")
    fun getAllReminders() : List<Reminder>

    @Query("SELECT * FROM reminders_table ORDER BY reminderId DESC")
    fun observeReminders() : LiveData<List<Reminder>>

    @Query("SELECT * FROM reminders_table ORDER BY reminderId DESC LIMIT 1")
    suspend fun getNewReminder() : Reminder?
}