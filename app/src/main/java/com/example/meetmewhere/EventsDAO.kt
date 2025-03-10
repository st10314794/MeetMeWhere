package com.example.meetmewhere

import android.media.metrics.Event
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventsDAO{
    @Insert
    fun insertEvent(events: Event)

    @Query("SELECT * FROM events")
    fun getAllEvents(): LiveData<List<Events>>
}