package com.example.myapp.admin

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myapp.FirebaseConnection
import com.example.myapp.R
import com.example.myapp.Users
import com.example.myapp.admin.home.FragmentHomeAdmin
import com.example.myapp.admin.home.HomeFragment
import com.example.myapp.admin.total.FragmentSimTotal
import com.example.myapp.admin.users.FragmentUsers
import com.example.myapp.databinding.ActivityAdminBinding
import com.google.firebase.messaging.FirebaseMessaging


class AdminActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding : ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setOnNavigationItemSelectedListener() {
            val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
            if (currentFragment is FragmentHomeAdmin) {
                when (it.itemId) {
                    R.id.navi_home -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentHomeAdmin_self)
                    R.id.navi_smile -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentHomeAdmin_to_fragmentInventory)
                    R.id.navi_pets -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentHomeAdmin_to_fragmentSales)
                    R.id.navi_sun -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentHomeAdmin_to_fragmentUsers)
                }
            } else if (currentFragment is FragmentInventory) {
                when (it.itemId) {
                    R.id.navi_home -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentInventory_to_fragmentHomeAdmin)
                    R.id.navi_smile -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentInventory_self)
                    R.id.navi_pets -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentInventory_to_fragmentSales)
                    R.id.navi_sun -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentInventory_to_fragmentUsers)
                }
            } else if (currentFragment is FragmentSales) {
                when (it.itemId) {
                    R.id.navi_home -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSales_to_fragmentHomeAdmin)
                    R.id.navi_smile -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSales_to_fragmentInventory)
                    R.id.navi_pets -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSales_self)
                    R.id.navi_sun -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSales_to_fragmentUsers)
                }
            } else {
                when (it.itemId) {
                    R.id.navi_home -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentUsers_to_fragmentHomeAdmin)
                    R.id.navi_smile -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentUsers_to_fragmentInventory)
                    R.id.navi_pets -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentUsers_to_fragmentSales)
                    R.id.navi_sun -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentUsers_self)
                }
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_navigation_menu, menu)
        return true
    }
}