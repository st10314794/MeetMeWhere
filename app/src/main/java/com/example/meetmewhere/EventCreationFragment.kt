package com.example.meetmewhere

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.meetmewhere.databinding.FragmentEventCreationBinding
import com.example.meetmewhere.databinding.FragmentEventsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventCreationFragment : Fragment() {

    private var _binding: FragmentEventCreationBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = AppDatabase.getDatabase(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edtDate.setOnClickListener{
            showDatePicker()
        }



        binding.saveButton.setOnClickListener{

            val title = binding.editTextTextEventTitle.text.toString()
            val desc = binding.edtDescription.text.toString()
            val date = binding.edtDate.text.toString()
            val location = binding.edtLocation.text.toString()

            if(binding.editTextTextEventTitle.text.toString().isEmpty()){
                binding.editTextTextEventTitle.error = "Title is required"
                return@setOnClickListener
            }

            if(desc.isEmpty()) {
                binding.edtDescription.error = "Description is required"
                return@setOnClickListener
            }


            if(binding.edtDate.text.toString().isEmpty()) {
                binding.edtDate.error = "Date is required"
                return@setOnClickListener
            }


            if(binding.edtLocation.text.toString().isEmpty()) {
                binding.edtLocation.error = "Location is required"
                return@setOnClickListener
            }

            addEvent(title, desc, date, date, location)
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            binding.edtDate.setText(date) // Show selected date in EditText
        }, year, month, day).show()
    }

    private fun addEvent(title:String, desc : String, date: String, time: String, location: String){
        val event = Events(title = title, description = desc, date = date, time = time, location = location)

        CoroutineScope(Dispatchers.IO).launch {
            val insertedID  = db.eventsDao().insertEvent(event)

            if (insertedID > 0) {
                val events = db.eventsDao().getAllEvents()  // Create a synchronous version of the query
                requireActivity().runOnUiThread {
                    Log.d("EventDetails", "Fetched events: $events")
                    Toast.makeText(requireContext(),"Event saved successfully!",Toast.LENGTH_SHORT).show()
                }
            }else{
                requireActivity().runOnUiThread {
                    Log.e("EventDetails", "Event insertion failed")
                    Toast.makeText(requireContext(), "Failed to save event!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}