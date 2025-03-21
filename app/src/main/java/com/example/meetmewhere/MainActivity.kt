package com.example.meetmewhere

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.meetmewhere.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    //Declare variable for binding project
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflate the layout using the binding class

        binding = ActivityMainBinding.inflate(layoutInflater)

        //setting the activities content view to the root view of the binding
        setContentView(binding.root)


//        replaceFragment(EventsFragment())
//
//        binding.bottomNav.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.events_menu -> replaceFragment(EventsFragment())
//                R.id.addEvent_menu -> replaceFragment(EventCreationFragment())
//                R.id.profile_menu -> replaceFragment(ProfileFragment())
//            }
//            true
//        }

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.events_menu -> findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.eventsFragment)
                R.id.addEvent_menu -> findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.eventCreationFragment)
                R.id.profile_menu -> findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.profileFragment)
            }
            true
        }


////        db = AppDatabase.getDatabase(applicationContext)
//        auth = Firebase.auth
//
//        try {
//            db = AppDatabase.getDatabase(applicationContext)
//        } catch (e: Exception) {
//            Log.e("DatabaseError", "Error initializing database", e)
//        }
//
////        val username = "user"
////        val password = "pass"
//
//        binding.loginButton.setOnClickListener{
//           val enteredEmail = binding.editTextTextEmail.text.toString()
//            val enteredPassword = binding.editTextTextPassword.text.toString()
//            //login call here
////            loginUser(enteredUsername, enteredPassword)
//            loginUserFireBase(enteredEmail, enteredPassword)
////            if ((enteredUsername == username) && (enteredPassword == password)) {
////                Toast.makeText(this, "You have logged in successfully", Toast.LENGTH_SHORT).show()
////
////                val eventIntent = Intent(this, EventDetails::class.java)
////                eventIntent.putExtra("username",username)
////                startActivity(eventIntent)
////            }else{
////                Toast.makeText(this,"Invalid login details",Toast.LENGTH_SHORT).show()
////            }
//        }
//
//        binding.buttonSignUp.setOnClickListener{
//            val intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)
//        }
    }

//    private fun replaceFragment(fragment: Fragment){
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.frameLayout, fragment).commit()
//    }

    private fun replaceFragment(fragment: Fragment) {
        // Get the NavController from the NavHostFragment
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Navigate to the specified fragment
        navController.navigate(R.id.action_EventsFragment_to_EventCreationFragment) // Use appropriate action ID from nav_graph
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


    private fun loginUserFireBase(email : String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@MainActivity,
                        "You have logged in successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    val eventIntent = Intent(this@MainActivity, EventDetails::class.java)
                    eventIntent.putExtra("email",email)
                    startActivity(eventIntent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }

    }
}