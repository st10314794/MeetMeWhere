package com.example.meetmewhere

import android.media.metrics.Event
import android.media.metrics.NetworkEvent
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EventsDAO{
    @Insert
    fun insertEvent(events: Events)

    @Query("SELECT * FROM events")
    fun getAllEvents(): LiveData<List<Events>>

    @Delete
    fun deleteEvent(events: Events)

    @Update
    fun editEvent(event: Events)
}