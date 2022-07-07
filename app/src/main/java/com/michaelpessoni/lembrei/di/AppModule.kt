package com.michaelpessoni.lembrei.di

import android.content.Context
import androidx.room.Room
import com.michaelpessoni.lembrei.data.local.RemindersDao
import com.michaelpessoni.lembrei.data.local.RemindersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): RemindersDatabase {
        return Room.databaseBuilder(
            appContext,
            RemindersDatabase::class.java,
            "reminders_db"
        )
            .build()
    }

    @Provides
    @Singleton
    fun providesDao(database: RemindersDatabase) : RemindersDao {
        return database.remindersDao()
    }
}