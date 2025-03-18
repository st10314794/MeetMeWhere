package com.example.meetmewhere

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.meetmewhere.databinding.ActivityEventDetailsBinding
import com.example.meetmewhere.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class EventDetails : AppCompatActivity() {
    //Declare variable for binding project
    private lateinit var binding: ActivityEventDetailsBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {

        //Inflate the layout using the binding class
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(applicationContext)

        val email = intent.getStringExtra("email")

        if (email !=null){
            binding.tvWelcomeMessage.text = "Welcome ${email}"
        }else{
            binding.tvWelcomeMessage.text = "Welcome"
        }

//        val datePicker : DatePicker = binding.datePicker
//        val today = Calendar.getInstance()
//        datePicker.init(
//            today.get(Calendar.YEAR),
//            today.get(Calendar.MONDAY),
//            today.get(Calendar.DAY_OF_MONTH)
//        ){ view, year, month, day ->
//            val msg = "You selected $day/${month+1}/$year"
//            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
//        }
        binding.editTextText3.setOnClickListener{
            showDatePicker()
        }



        binding.saveButton.setOnClickListener{
            val title = binding.editTextTextEventTitle.text.toString()
            val desc = binding.editTextTextMultiLine.text.toString()
            val date = binding.editTextText3.text.toString()
            val location = binding.editTextText4.text.toString()

            if(binding.editTextTextEventTitle.text.toString().isEmpty()){
                binding.editTextTextEventTitle.error = "Title is required"
                return@setOnClickListener
            }

            if(desc.isEmpty()) {
                binding.editTextTextMultiLine.error = "Description is required"
                return@setOnClickListener
            }


            if(binding.editTextText3.text.toString().isEmpty()) {
                binding.editTextText3.error = "Date is required"
                return@setOnClickListener
            }


            if(binding.editTextText4.text.toString().isEmpty()) {
                binding.editTextText4.error = "Location is required"
                return@setOnClickListener
            }



                addEvent(title, desc, date, date, location)
                Toast.makeText(this,"Successfully saved",Toast.LENGTH_SHORT).show()

        }


        //ImageView button --> screen to display events
        binding.buttonViewEvents.setOnClickListener{
            val eventDisplayIntent = Intent(this, EventsDisplayActivity::class.java)
            startActivity(eventDisplayIntent)
        }


    }
    private fun showDatePicker(){
//    val today = Calendar.getInstance()
//    datePicker.init(
//        today.get(Calendar.YEAR),
//        today.get(Calendar.MONDAY),
//        today.get(Calendar.DAY_OF_MONTH)
//    ){ view, year, month, day ->
//        val msg = "You selected $day/${month+1}/$year"
//        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
//    }
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            binding.editTextText3.setText(date) // Show selected date in EditText
        }, year, month, day).show()
    }


    private fun addEvent(title:String, desc : String, date: String, time: String, location: String){
        val event = Events(title = title, description = desc, date = date, time = time, location = location)

        CoroutineScope(Dispatchers.IO).launch {
            db.eventsDao().insertEvent(event)


            CoroutineScope(Dispatchers.IO).launch {
                val events = db.eventsDao().getAllEvents()  // Create a synchronous version of the query
                runOnUiThread {
                    Log.d("EventDetails", "Fetched events: $events")

                }
            }

        }

    }
}

