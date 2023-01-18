package com.example.myapp.admin.home

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.databinding.FragmentHomeBuyBinding
import com.example.myapp.databinding.FragmentPayBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class FragmentHomeBuy : Fragment(R.layout.fragment_home_buy) {

    private lateinit var binding: FragmentHomeBuyBinding
    private fun replaceFragment(fragment : Fragment){
        val childFragment: Fragment = fragment
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.child_fragment_container, childFragment).commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        replaceFragment(FragmentInfoInBuy())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBuyBinding.inflate(inflater, container, false)
        val view: View = binding.getRoot()

        binding.cameraBtn.setOnClickListener {
            replaceFragment(CameraFragment())
        }
        binding.infoBtn.setOnClickListener {
            replaceFragment(FragmentInfoInBuy())
        }

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHomeBuy_to_payFragment)
        }

        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.supportActionBar!!.setDisplayUseLogoEnabled(false)
        }


        hideBottomNav()


        return view

    }

    private fun hideBottomNav(){
        // Get the activity
        val activity = activity as AppCompatActivity?

        // Get the bottom navigation view
        val bottomNav = activity!!.findViewById<BottomNavigationView>(R.id.potentialBottomNav)
        val bottomNav2 = activity!!.findViewById<BottomNavigationView>(R.id.presentBottomNav)
        // Hide the bottom navigation view
        bottomNav.visibility = View.GONE
        bottomNav2.visibility = View.GONE
    }


}