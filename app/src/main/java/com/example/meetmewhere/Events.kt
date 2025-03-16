package com.example.meetmewhere

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Events(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val location: String,
)