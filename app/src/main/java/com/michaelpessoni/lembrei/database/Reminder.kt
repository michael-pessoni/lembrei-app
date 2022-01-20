package com.michaelpessoni.lembrei.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "reminders_table")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val reminderId: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "priority")
    val priority: Boolean
)
