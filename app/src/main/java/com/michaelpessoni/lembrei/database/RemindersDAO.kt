package com.michaelpessoni.lembrei.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RemindersDAO {

    @Insert
    suspend fun insert(reminder: Reminder)

    @Update
    suspend fun update(reminder: Reminder)

    @Query("SELECT * from reminders_table WHERE reminderId = :id")
    suspend fun get(id: Int) : Reminder?

    @Query("DELETE from reminders_table WHERE reminderId = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * from reminders_table")
    fun getAllReminders() : List<Reminder>?

    @Query("SELECT * from reminders_table WHERE date = :today")
    suspend fun getToday(today: String) : Reminder?
}