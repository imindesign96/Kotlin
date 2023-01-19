package com.example.myapp.admin.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapp.FirebaseConnection
import com.example.myapp.R
import com.example.myapp.admin.users.UsersData
import com.example.myapp.admin.users.UsersViewModel
import com.example.myapp.databinding.FragmentConfirmationBinding

class ConfirmationFragment : Fragment() {

    private val args: ConfirmationFragmentArgs by navArgs()
    private val userViewModel : UsersViewModel by activityViewModels()
    private lateinit var usersData : UsersData
    private lateinit var binding: FragmentConfirmationBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmationBinding.inflate(inflater, container, false)
        userViewModel.setSim()
        val user = args.user

        binding.emailEt.setText(user!!.email)
        binding.userAddress.setText(user!!.userAddress)
        binding.userFullName.setText(user!!.fullName)

        userViewModel.result.observe(viewLifecycleOwner) {result->
            if (result == "Success" && userViewModel.chosenSimService.value!!.sim!!.simCode!!.isNotEmpty()) {
                userViewModel.chosenSimService.observe(viewLifecycleOwner) {
                    binding.simEt.setText("Phone Number:" + it.sim!!.phoneNumber +  " Data: " + it.data!!.name + "  Service:" + it.service!!.name )
                }
            }
        }

        userViewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.totalPrice.setText(it.toString())
        }
        binding.payBtn.setOnClickListener {
            val action = ConfirmationFragmentDirections.actionConfirmationFragmentToPayFragment()
            findNavController().navigate(action)
        }

        binding.backBtn.setOnClickListener {
            val action = ConfirmationFragmentDirections.actionConfirmationFragmentToFragmentHomeBuy()
            findNavController().navigate(action)
        }
        return binding.root
    }
}