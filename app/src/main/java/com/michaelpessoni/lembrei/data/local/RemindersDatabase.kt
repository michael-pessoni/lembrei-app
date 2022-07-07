package com.michaelpessoni.lembrei.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.michaelpessoni.lembrei.data.Reminder

@Database(entities = [Reminder::class], version = 2)
abstract class RemindersDatabase : RoomDatabase() {

    abstract  fun remindersDao(): RemindersDao

}