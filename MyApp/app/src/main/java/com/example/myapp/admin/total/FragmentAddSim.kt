package com.example.myapp.admin.total

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapp.FirebaseConnection
import com.example.myapp.R
import com.example.myapp.databinding.FragmentAddSimBinding
import com.example.myapp.databinding.FragmentSimTotalBinding
import kotlinx.coroutines.runBlocking

class FragmentAddSim: Fragment(R.layout.fragment_add_sim) {
    private lateinit var binding: FragmentAddSimBinding
    private val firebaseConnection : FirebaseConnection by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddSimBinding.inflate(inflater, container, false)

        binding.addSim.setOnClickListener {
            val phoneNumber = binding.tvPhoneNumber.text.toString()
            val price = binding.tvPriceSim.text.toString()
            firebaseConnection.generateSimCode(phoneNumber,price)
            firebaseConnection.addSimResult.observe(viewLifecycleOwner) {
                if (it == "Success") {
                    backToSimTotal()
                } else {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.cancel.setOnClickListener {
            backToSimTotal()
        }
        return binding.root
    }

    private fun backToSimTotal() {
        val action = FragmentAddSimDirections.actionFragmentAddSimToFragmentSimTotal()
        findNavController().navigate(action)
    }
}