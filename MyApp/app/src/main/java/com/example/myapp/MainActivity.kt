package com.example.myapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapp.admin.FragmentSales
import com.example.myapp.admin.home.HomeFragment
import com.example.myapp.admin.total.FragmentSimTotal
import com.example.myapp.Users
import com.example.myapp.admin.FragmentInventory
import com.example.myapp.admin.home.FragmentHomeAdmin
import com.example.myapp.admin.home.PayFragment
import com.example.myapp.admin.home.SharedViewModel
import com.example.myapp.admin.users.UsersAdapter
import com.example.myapp.admin.users.UsersData
import com.example.myapp.admin.users.UsersViewModel
import com.example.myapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.remote.Datastore
import java.util.prefs.Preferences


class MainActivity : AppCompatActivity() {

    private val userViewModel: UsersViewModel by viewModels()
    private lateinit var usersData : UsersData
    private lateinit var binding: ActivityMainBinding
    private lateinit var signOutBtn: Button
    private lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usersData = intent.getParcelableExtra("UsersData")!!
        userViewModel.setUser(usersData)
        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController
        // Make sure actions in the ActionBar get propagated to the NavController
        supportActionBar?.setDisplayShowHomeEnabled(true)
        //supportActionBar?.setLogo(R.drawable.logomobile)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide()

        setupActionBarWithNavController(navController)

//        when (usersData.status) {
//            "未支払い" -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_payFragment)
//            "支払済み" -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_self)
//            else -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_self)
//        }
    }

    /**
     * Enables back button support. Simply navigates one element up on the stack.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (usersData.role == Users.POTENTIAL_USER) {
            binding.potentialBottomNav.isVisible = true
            binding.presentBottomNav.isVisible = false
            menuInflater.inflate(R.menu.potential_user_bottomnav, menu)
        } else {
            binding.potentialBottomNav.isVisible = false
            binding.presentBottomNav.isVisible = true
            menuInflater.inflate(R.menu.present_user_bottomnav, menu)
        }
        return true
    }

}

