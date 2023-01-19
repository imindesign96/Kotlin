package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapp.admin.users.UsersData
import com.example.myapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener{
            val username = binding.usernameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            if(username.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass == confirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                        if (it.isSuccessful){
                            dbRef = FirebaseDatabase.getInstance("https://my-android-app-7f2c4-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
                            val savedEmail = email.replace(".",",")
                            val user = UsersData(username,Users.POTENTIAL_USER,email)
                            dbRef.child("UsersData").child(savedEmail).setValue(user)
                            val intent = Intent(this, SignInActivity::class.java)
                            intent.putExtra("signupFinished","finished")
                            startActivity(intent)
                        }else {
                            Toast.makeText(this , it.exception.toString() , Toast.LENGTH_SHORT).show()
                        }
                    }
                }else {
                    Toast.makeText(this , "Password is not match" , Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this , "Empty Fields are not Allowed !!" , Toast.LENGTH_SHORT).show()
            }
        }
    }
}