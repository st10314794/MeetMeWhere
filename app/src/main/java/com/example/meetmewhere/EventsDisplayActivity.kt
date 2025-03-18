package com.example.meetmewhere

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetmewhere.databinding.ActivityEventsDisplayBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// TODO : Add functionality to Edit and Delete buttons

class EventsDisplayActivity : AppCompatActivity() {
    private lateinit var binding:ActivityEventsDisplayBinding
    private lateinit var adapter: EventAdapter
    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inflate
        binding = ActivityEventsDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(applicationContext)

        adapter = EventAdapter(emptyList(), onEditClick = {event -> editEvent(event) }, onDeleteClick = {event -> deleteEvent(event) })
        binding.rvEvents.layoutManager = LinearLayoutManager(this)
        binding.rvEvents.adapter = adapter


        getEvents()
//      getEventsHardCode()



        }//end onCreate

//    private fun getEvents() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val events = db.eventsDao().getAllEvents()
//            runOnUiThread{
//                adapter.updateEvents(events)
//            }
//
//        }
//    }

    private fun getEvents() {
        // Observe the LiveData with a lambda function
        db.eventsDao().getAllEvents().observe(this) { events ->
            // Update the adapter with the new events list
            adapter.updateEvents(events)
        }
    }
    private fun editEvent(event: Events){

    }
    private fun deleteEvent(event: Events){
        CoroutineScope(Dispatchers.IO).launch{
            db.eventsDao().deleteEvent(event)
            runOnUiThread{
                getEvents()
            }
        }
    }



    private fun getEventsHardCode() {
        // Hardcoded list of events for testing
        val testEvents = listOf(
            Events(1, "Description 1", "2023-05-01", "10:00 AM", "Location 1", "Loc"),
            Events(2, "Description 2", "2023-06-01", "11:00 AM", "Location 2", "Loc"),
            Events(3, "Description 3", "2023-07-01", "12:00 PM", "Location 3", "Loc")
        )


        // Update the adapter with the hardcoded events
        adapter.updateEvents(testEvents)
    }
}
