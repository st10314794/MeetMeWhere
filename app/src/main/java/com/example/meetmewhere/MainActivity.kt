package com.example.meetmewhere

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.meetmewhere.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    //Declare variable for binding project
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflate the layout using the binding class

        binding = ActivityMainBinding.inflate(layoutInflater)

        //setting the activities content view to the root view of the binding
        setContentView(binding.root)

        db = AppDatabase.getDatabase(applicationContext)

//        val username = "user"
//        val password = "pass"

        binding.loginButton.setOnClickListener{
           val enteredUsername = binding.editTextTextUsername.text.toString()
            val enteredPassword = binding.editTextTextPassword.text.toString()
            //login call here
            loginUser(enteredUsername, enteredPassword)
//            if ((enteredUsername == username) && (enteredPassword == password)) {
//                Toast.makeText(this, "You have logged in successfully", Toast.LENGTH_SHORT).show()
//
//                val eventIntent = Intent(this, EventDetails::class.java)
//                eventIntent.putExtra("username",username)
//                startActivity(eventIntent)
//            }else{
//                Toast.makeText(this,"Invalid login details",Toast.LENGTH_SHORT).show()
//            }
        }

        binding.buttonSignUp.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(username: String, password: String){
        CoroutineScope(Dispatchers.IO).launch {
            val existingUser = db.usersDao().getUserByUsername(username)

            runOnUiThread{
//                if(existingUser != null){
//                    if ((existingUser.password) == (password)){
//                        Toast.makeText(this@MainActivity, "You have logged in successfully", Toast.LENGTH_SHORT).show()
//                        return@runOnUiThread
//                    }else{
//                        Toast.makeText(this@MainActivity, "Invalid login details", Toast.LENGTH_SHORT).show()
//                        return@runOnUiThread
//                    }
//                } else{
//                    Toast.makeText(this@MainActivity, "User does not exist", Toast.LENGTH_SHORT).show()
//                    return@runOnUiThread
//                }
                when{
                    existingUser == null -> Toast.makeText(this@MainActivity, "User does not exist", Toast.LENGTH_SHORT).show()
                    existingUser.password != password -> Toast.makeText(this@MainActivity, "Invalid login details", Toast.LENGTH_SHORT).show()
                    else -> {
                        Toast.makeText(
                            this@MainActivity,
                            "You have logged in successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        val eventIntent = Intent(this@MainActivity, EventDetails::class.java)
                        eventIntent.putExtra("username",username)
                        startActivity(eventIntent)
                    }
                }
            }//endrunonui
        }//end corrouteine
    }//end of login user
}