package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.myapp.admin.users.UsersData
import com.example.myapp.admin.users.UsersViewModel
import com.example.myapp.databinding.ActivityGetDataUsersBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class GetInfoUser : AppCompatActivity() {

    private lateinit var binding: ActivityGetDataUsersBinding
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: UsersData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetDataUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()


            binding.updateInfoUserBtn.setOnClickListener {
                val address = binding.userAddress.text.toString()
                val fullName = binding.userFullName.text.toString()
                val email = binding.emailEt.text.toString()

                updateData(fullName, address, email)

            }
        }
    
                private fun updateData(
                    fullName: String,
                    address: String,
                    email: String
                ) {

                    database =
                        FirebaseDatabase.getInstance().getReference("User").child("UsersData")


                    val savedEmail = email.replace(".", ",")

//                    val data = UsersData(fullName,,address,"SimPrice",null,null,null,Users.POTENTIAL_USER,email)
//                    database.child(savedEmail).setValue(data).addOnSuccessListener {
//
//                        Toast.makeText(this, "Successfuly Updated", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(this, MainActivity::class.java)
//                        startActivity(intent)
//
//                    }.addOnFailureListener {
//
//                        Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()
//
//                    }
                }

            }

//    private fun readData(email: String) {
                //        val savedEmail = email.replace(".",",")
                //        database = FirebaseDatabase.getInstance().getReference("User")
                //        database.child("UsersData").child(savedEmail).get().addOnSuccessListener {
                //
                //            if (it.exists()){
                //
                //                val userName = it.child("userName").value
                //                val email = it.child("email").value
                //
                //                binding.emailEt.text = email as Editable?
                //                binding.userFullName.text = userName as Editable?
                //
                //                Toast.makeText(this,"Successfuly Read",Toast.LENGTH_SHORT).show()
                //            }else{
                //
                //                Toast.makeText(this,"User Doesn't Exist",Toast.LENGTH_SHORT).show()
                //
                //
                //            }
                //
                //        }.addOnFailureListener{
                //
                //            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
                //
                //
                //        }
                //
                //
                //
                //    }

