package com.example.meetmewhere

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.meetmewhere.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inflate
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.loginButton.setOnClickListener{
            val enteredEmail = binding.edtEmail.text.toString()
            val enteredPassword = binding.edtPassword.text.toString()

            loginUserFirebase(enteredEmail, enteredPassword)
        }

        binding.buttonSignUp.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUserFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "You have logged in successfully", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "signWithEmail:success")
                    val user = auth.currentUser
                    val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    mainIntent.putExtra("email", email)
                    startActivity(mainIntent)
                }else{
                    Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

}