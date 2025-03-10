package com.example.meetmewhere

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.meetmewhere.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //Declare variable for binding project
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflate the layout using the binding class

        binding = ActivityMainBinding.inflate(layoutInflater)

        //setting the activities content view to the root view of the binding
        setContentView(binding.root)

        val username = "user"
        val password = "pass"

        binding.loginButton.setOnClickListener{
           val enteredUsername = binding.editTextTextUsername.text.toString()
            val enteredPassword = binding.editTextTextPassword.text.toString()
            if ((enteredUsername == username) && (enteredPassword == password)) {
                Toast.makeText(this, "You have logged in successfully", Toast.LENGTH_SHORT).show()

                val eventIntent = Intent(this, EventDetails::class.java)
                startActivity(eventIntent)
            }else{
                Toast.makeText(this,"Invalid login details",Toast.LENGTH_SHORT).show()
            }
        }
    }
}