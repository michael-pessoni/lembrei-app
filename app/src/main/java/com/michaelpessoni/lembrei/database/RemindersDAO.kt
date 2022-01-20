package com.michaelpessoni.lembrei.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.*

@Dao
interface RemindersDAO {

    @Insert
    fun insert(reminder: Reminder)

    @Update
    fun update(reminder: Reminder)

    @Query("SELECT * from reminders_table WHERE reminderId = :id")
    fun get(id: Long) : Reminder?

    @Query("DELETE from reminders_table WHERE reminderId = :id")
    fun delete(id: Long)

    @Query("SELECT * from reminders_table ORDER BY date ASC")
    fun getAllReminders()

    @Query("SELECT * from reminders_table WHERE date = :today")
    fun getToday(today: Date)
}