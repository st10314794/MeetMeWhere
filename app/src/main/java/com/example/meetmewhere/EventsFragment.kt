package com.example.meetmewhere

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetmewhere.databinding.FragmentEventsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsFragment : Fragment() {

    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventAdapter: EventAdapter
    private val eventsList = mutableListOf<Events>()

    private lateinit var db : AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = AppDatabase.getDatabase(requireContext())
    }
    //Creates and returns fragments UI
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_events, container, false)
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //Initialis erecyclverview
        eventAdapter = EventAdapter(emptyList(), onEditClick = {event -> editEvent(event) }, onDeleteClick = {event -> deleteEvent(event) })
        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.adapter = eventAdapter

        getEvents()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun getEvents() {
        // Observe the LiveData with a lambda function
        db.eventsDao().getAllEvents().observe(viewLifecycleOwner) { events ->
            // Update the adapter with the new events list
            eventAdapter.updateEvents(events)
        }
    }
    private fun editEvent(event: Events) {
        TODO("Not yet implemented")
    }

    private fun deleteEvent(event: Events) {
        CoroutineScope(Dispatchers.IO).launch{
            db.eventsDao().deleteEvent(event)
            //Cannot just use runonui thread in fragments
            requireActivity().runOnUiThread{
                getEvents()
            }
        }
    }

}