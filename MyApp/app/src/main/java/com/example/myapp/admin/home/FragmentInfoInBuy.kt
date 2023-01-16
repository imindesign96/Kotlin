package com.example.myapp.admin.home

import android.os.Bundle
import android.text.Editable
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
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class FragmentInfoInBuy : Fragment(R.layout.fragment_info_in_buy) {

    private lateinit var binding : FragmentInfoInBuyBinding
    private lateinit var dbRef : DatabaseReference
    private lateinit var user : ArrayList<UsersData>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var email : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoInBuyBinding.inflate(inflater,container,false)
        val view: View = binding.root
        firebaseAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("User").child("UsersData")
        val sharedViewModel = activity?.let { ViewModelProvider(it).get(SharedViewModel::class.java) }
        email = firebaseAuth.currentUser?.email.toString() // lay duoc email
        val simPrice = sharedViewModel?.data?.value.toString() // lay gia tri sim dang chon
        binding.emailEt.setText(email) //set email cho input text


        binding.updateInfoUserBtn.setOnClickListener {
            val address = binding.userAddress.text.toString()
            val name = binding.userFullName.text.toString()

                updateData(name, address,simPrice,email)

        }


        return view

    }

    private fun updateData(
        name: String,
        address: String,
        simPrice: String?,
        email : String
    ) {
        val status = "未支払い"
        val savedEmail = email.replace(".", ",")
        val data = UsersData(0,name,null,address,simPrice,null,null,status,
            Users.POTENTIAL_USER,email,null)
        dbRef.child(savedEmail).setValue(data).addOnSuccessListener {
            findNavController().navigate(R.id.action_fragmentHomeBuy_to_payFragment)
            Toast.makeText(context, "Successfuly Updated", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show()

        }
    }


}