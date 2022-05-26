package com.michaelpessoni.lembrei.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.michaelpessoni.lembrei.data.Reminder

@Database(entities = [Reminder::class], version = 2)
abstract class RemindersDatabase : RoomDatabase() {

    abstract  val remindersDatabaseDAO: RemindersDAO

    companion object {

        @Volatile
        private var INSTANCE: RemindersDatabase? = null


        fun getInstance(context: Context): RemindersDatabase {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
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