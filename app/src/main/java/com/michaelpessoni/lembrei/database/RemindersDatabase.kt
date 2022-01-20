package com.michaelpessoni.lembrei.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Reminder::class], version = 1, exportSchema = false)
abstract class RemindersDatabase : RoomDatabase(){

    abstract val remindersDatabase: RemindersDatabase

    companion object {

        @Volatile
        private var INSTANCE: RemindersDatabase? = null

        fun getInstance(context: Context): RemindersDatabase? {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    Room.databaseBuilder(
                        context.applicationContext,
                        RemindersDatabase::class.java,
                        "reminders_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }

                INSTANCE = instance

                return instance
            }
        }
    }

}