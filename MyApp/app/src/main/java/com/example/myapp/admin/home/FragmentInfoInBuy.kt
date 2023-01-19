package com.example.myapp.admin.home

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapp.FirebaseConnection
import com.example.myapp.R
import com.example.myapp.Users
import com.example.myapp.admin.total.SimData
import com.example.myapp.admin.users.UsersData
import com.example.myapp.admin.users.UsersViewModel
import com.example.myapp.databinding.FragmentInfoInBuyBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class FragmentInfoInBuy : Fragment(R.layout.fragment_info_in_buy) {

    private val userViewModel : UsersViewModel by activityViewModels()
    private val firebaseConnection : FirebaseConnection by activityViewModels()
    private lateinit var binding : FragmentInfoInBuyBinding
    private lateinit var dbRef : DatabaseReference
    private lateinit var user : ArrayList<UsersData>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var email : String
    private lateinit var sim : SimData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoInBuyBinding.inflate(inflater,container,false)
        val view: View = binding.root
        val sharedViewModel = activity?.let { ViewModelProvider(it).get(SharedViewModel::class.java) }
        firebaseAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("User").child("UsersData")
        email = firebaseAuth.currentUser?.email.toString() // lay duoc email
        val simPrice = sharedViewModel?.data?.value.toString() // lay gia tri sim dang chon
        binding.emailEt.setText(email) //set email cho input text


        binding.updateInfoUserBtn.setOnClickListener {
            val address = binding.userAddress.text.toString()
            val name = binding.userFullName.text.toString()
            firebaseConnection.getSimResult.observe(viewLifecycleOwner){
                if (it == "Success") {
                    updateData(address, name)
                }
            }
            updateData(name, address)
            UsersData().fullName
            UsersData().userName
            UsersData().userAddress
        }

        return view

    }

    private fun updateData(
        name: String,
        address: String,
    ) {
        val status = "未支払い"
        firebaseConnection.selectedSim.observe(viewLifecycleOwner) { simData ->
            sim = simData
            Log.d("BUYSIM", "SET SIMDATA ${simData}")
            userViewModel.setSimData(sim)
            userViewModel.userInfo.observe(viewLifecycleOwner) {
                val data = UsersData(
                    name,
                    it.userName,
                    address,
                    null,
                    status,
                    Users.POTENTIAL_USER,
                    it.email,
                    sim
                )
                val savedEmail = it.email.toString().replace(".", ",")
                dbRef.child(savedEmail).setValue(data).addOnSuccessListener {
                    val action = FragmentHomeBuyDirections.actionFragmentHomeBuyToConfirmationFragment(data)
                    findNavController().navigate(action)
                    Toast.makeText(context, "Successfuly Updated", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}