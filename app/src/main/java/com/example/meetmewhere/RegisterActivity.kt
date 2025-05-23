package com.example.meetmewhere

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.meetmewhere.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    //Declare variable for binding project
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inflate the layout using the binding class
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Initialize Firebase Auth
        auth = Firebase.auth


        db = AppDatabase.getDatabase(applicationContext)

        binding.buttonSignUp.setOnClickListener {
            val name = binding.editTextTextName.text.toString()
            val email = binding.editTextTextEmail.text.toString()
            val password = binding.editTextTextPasswordSignUp.text.toString()
            val confirmPassword = binding.editTextTextPasswordSignUpConfirm.text.toString()

            //checks
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                binding.editTextTextPasswordSignUpConfirm.error = "Passwords do not match"
                return@setOnClickListener
            }



            createNewUser(name, email, password)
//            createNewUserFirebase(email, password)
            createNewUserFirebase(name, email, password)

            val eventIntent = Intent(this, MainActivity::class.java)
            eventIntent.putExtra("username", name)
            startActivity(eventIntent)
//

        }


//    fun createNewUserFireBase(email :String, password: String){
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "createUserWithEmail:success")
//                    val user = auth.currentUser
//                    //Intent --> next screen (event)
//
//                    //updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        baseContext,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                   // updateUI(null)
//                }
//            }
//    }

//        fun createNewUser(username: String, email: String, password: String) {
//
//            val existingUser = db.usersDao().getUserByUsername(username)
//            if (existingUser != null) {
//                Toast.makeText(this, "This user already exists", Toast.LENGTH_SHORT).show()
//            } else {
//                val user = Users(name = username, email = email, password = password)
//                db.usersDao().insertUser(user)
//                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
//            }
//        }

        //test


    }

//    private fun createNewUserFireBase(email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "createUserWithEmail:success")
//                    val user = auth.currentUser
//                    //Intent --> next screen (event)
//
//                    //updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(
//                        baseContext,
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()
//                    // updateUI(null)
//                }
//            }
//    }

    private fun createNewUser(username: String, email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val existingUser = db.usersDao().getUserByUsername(username)

            if (existingUser != null) {
                // Switch to the main thread to show a Toast
                launch(Dispatchers.Main) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "This user already exists",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                val user = Users(name = username, email = email, password = password)
                db.usersDao().insertUser(user)

                // Switch to the main thread to show a success message
                launch(Dispatchers.Main) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


//    private fun createNewUserFirebase(email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    val user = auth.currentUser
//                } else {
//                    Toast.makeText(this@RegisterActivity, "Registration Failed", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//    }

    private fun createNewUserFirebase(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener{ updateTask ->
                            if (updateTask.isSuccessful) {
                                Toast.makeText(this@RegisterActivity, "Registration Successful", Toast.LENGTH_SHORT).show()

                            }
                        }
                } else {
                    Toast.makeText(this@RegisterActivity, "Registration Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}






