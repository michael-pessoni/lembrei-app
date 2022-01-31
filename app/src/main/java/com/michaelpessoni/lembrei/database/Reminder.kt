package com.michaelpessoni.lembrei.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders_table")
data class Reminder(

    @PrimaryKey(autoGenerate = true)
    var reminderId: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

    @ColumnInfo(name = "date")
    var date: String = "",

    @ColumnInfo(name = "priority")
    var priority: Boolean = false
)
