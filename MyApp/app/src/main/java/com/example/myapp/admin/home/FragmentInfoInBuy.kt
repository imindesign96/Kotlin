package com.example.myapp.admin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.Users
import com.example.myapp.admin.users.UsersData
import com.example.myapp.databinding.FragmentInfoInBuyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class FragmentInfoInBuy : Fragment(R.layout.fragment_info_in_buy) {

    private lateinit var binding : FragmentInfoInBuyBinding
    private lateinit var dbRef : DatabaseReference
    private lateinit var user : ArrayList<UsersData>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userName : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedViewModel = activity?.let { ViewModelProvider(it).get(SharedViewModel::class.java) }
        binding = FragmentInfoInBuyBinding.inflate(inflater,container,false)
        val view: View = binding.root
        firebaseAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("User").child("UsersData")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val email = firebaseAuth.currentUser?.email
                val saveEmail = email?.replace(".",",")
                    userName = saveEmail?.let {
                        dataSnapshot.child(it).child("userName").getValue(String::class.java)
                            .toString()
                    }.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
        val simPrice = sharedViewModel?.data?.value

        binding.updateInfoUserBtn.setOnClickListener {

            val phoneNumber = binding.userPhoneNumber.text.toString()
            val address = binding.userAddress.text.toString()
            val name = binding.userFullName.text.toString()
            val email = binding.emailEt.text.toString()

            updateData(name, phoneNumber, address, email,userName,simPrice)
        }


        return view

    }

    private fun updateData(
        name: String,
        phoneNumber: String,
        address: String,
        email: String,
        userName: String,
        simPrice: String?
    ) {

        val savedEmail = email.replace(".", ",")
        val data = UsersData(0,name,userName,address,simPrice,null,null,null,
            Users.POTENTIAL_USER,email,phoneNumber)
        dbRef.child(savedEmail).setValue(data).addOnSuccessListener {
            findNavController().navigate(R.id.action_fragmentHomeBuy_to_payFragment)
            Toast.makeText(context, "Successfuly Updated", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show()

        }
    }


}