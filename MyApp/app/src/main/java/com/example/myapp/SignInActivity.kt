package com.example.myapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.startActivity
import com.example.myapp.admin.AdminActivity
import com.example.myapp.admin.users.UsersData
import com.example.myapp.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var dbRef: DatabaseReference
    private lateinit var user : UsersData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Login With Google

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.loginWithFacebook.setOnClickListener {
            val intent = Intent(this, SignInWithPhone::class.java)
            startActivity(intent)
        }

        // Dang nhap bang email
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            Log.d("TAG LOI GI DAY", "LOGIN OK")
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val savedEmail = email.replace(".",",")
                        Log.d("savedemail", "Email: $savedEmail")
                        dbRef = FirebaseDatabase.getInstance("https://my-android-app-7f2c4-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
                        dbRef.child("UsersData").child(savedEmail).addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                user = snapshot.getValue(UsersData::class.java)!!
                                Log.d("savedemail", "Role: " +user.role)
                                user.role?.let { it1 -> moveToActivity(it1) }
                            }
                            override fun onCancelled(error: DatabaseError) {
                            }
                        })

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.loginWithGoogle.setOnClickListener {
            signInGoogle()
        }

    }

    private fun moveToActivity(role: Users) {
        if (role == Users.ADMIN) {
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }
        }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                Log.d("TAG LOI GI DAY", "LOGIN GOOGLE OK")
                updateUI(account)
            }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("TAG LOI GI DAY", "LOGIN GOOGLE OK")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val status = intent.getStringExtra("signupFinished")
        if(status == null && firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}



