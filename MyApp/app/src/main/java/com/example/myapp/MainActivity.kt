package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.loginandsignup.R
import com.example.loginandsignup.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var signOutBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("email")
        val displayName = intent.getStringExtra("name")
        val number = intent.getStringExtra("phoneNumber")

        binding.textView.text = email + "\n" + displayName + "\n" + number



        auth = FirebaseAuth.getInstance()
        signOutBtn = binding.logOutBtn

        signOutBtn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
        }

    }
}