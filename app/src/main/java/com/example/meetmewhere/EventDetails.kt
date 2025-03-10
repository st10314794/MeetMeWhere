package com.example.meetmewhere

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.meetmewhere.databinding.ActivityEventDetailsBinding
import com.example.meetmewhere.databinding.ActivityMainBinding

class EventDetails : AppCompatActivity() {
    //Declare variable for binding project
    private lateinit var binding: ActivityEventDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        //Inflate the layout using the binding class
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)


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

            if(binding.editTextTextEventTitle.text.toString().isEmpty()){
                binding.editTextTextEventTitle.error = "Title is required"
                return@setOnClickListener
            }else{
                Toast.makeText(this,"Successfully saved",Toast.LENGTH_SHORT).show()
            }
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
}

