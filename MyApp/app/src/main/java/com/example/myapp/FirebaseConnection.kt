package com.example.myapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapp.admin.users.UsersData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FirebaseConnection : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    val result = MutableLiveData<String>()
    val user = MutableLiveData<UsersData>()
    private val dbRef = FirebaseDatabase.getInstance("https://my-android-app-7f2c4-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")

    init {
        result.value = ""
    }
    fun login(email : String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                result.value = "Success"
            }
        }
    }

    fun register(email: String) {
        val savedEmail = email.replace(".",",")
        dbRef.child("UsersData").child(savedEmail).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user.value = snapshot.getValue(UsersData::class.java)!!
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

}