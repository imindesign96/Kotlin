package com.example.myapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapp.admin.FragmentSales
import com.example.myapp.admin.home.HomeFragment
import com.example.myapp.admin.total.FragmentSimTotal
import com.example.myapp.Users
import com.example.myapp.admin.home.PayFragment
import com.example.myapp.admin.users.UsersAdapter
import com.example.myapp.admin.users.UsersData
import com.example.myapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var signOutBtn: Button
    private lateinit var navController: NavController
    private lateinit var user : UsersData
    private lateinit var dbRef : DatabaseReference


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController
        // Make sure actions in the ActionBar get propagated to the NavController
        supportActionBar?.setDisplayShowHomeEnabled(true)
        //supportActionBar?.setLogo(R.drawable.logomobile)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        setupActionBarWithNavController(navController)

        val email = auth.currentUser?.email
        val saveEmail = email?.replace(".",",")

        dbRef =
            saveEmail?.let {
                FirebaseDatabase.getInstance().getReference("User").child("UsersData").child(
                    it
                ).child("status")
            }!!

        dbRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                when(task.result?.getValue(String::class.java)){
                    "未支払い" -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_payFragment)
                    "支払済み" -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_self)
                    else -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_self)
                }            } else {
                // handle error
            }
        }
        binding.bottomNavigationViewMain.setOnNavigationItemSelectedListener(){
            val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
            if (currentFragment is HomeFragment) {
                when(it.itemId){
                    R.id.navi_home -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_self)
                    R.id.navi_smile -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_fragmentSimTotal)
                    R.id.navi_pets -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_fragmentSales)
                    R.id.navi_sun -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_fragmentUsers)
                }
            } else if (currentFragment is FragmentSimTotal) {
                when(it.itemId){
                    R.id.navi_home -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSimTotal_to_homeFragment)
                    R.id.navi_smile -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSimTotal_self)
                    R.id.navi_pets -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSimTotal_to_fragmentSales)
                    R.id.navi_sun -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSimTotal_to_fragmentUsers)
                }
            } else if (currentFragment is FragmentSales) {
                when(it.itemId){
                    R.id.navi_home -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSales_to_homeFragment)
                    R.id.navi_smile -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSales_to_fragmentSimTotal)
                    R.id.navi_pets -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSales_self)
                    R.id.navi_sun -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentSales_to_fragmentUsers)
                }
            } else {
                when(it.itemId){
                    R.id.navi_home -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentUsers_to_homeFragment)
                    R.id.navi_smile -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentUsers_to_fragmentSimTotal)
                    R.id.navi_pets -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentUsers_to_fragmentSales)
                    R.id.navi_sun -> findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragmentUsers_self)
                }
            }
            true
        }
    }

    /**
     * Enables back button support. Simply navigates one element up on the stack.
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_navigation_menu, menu)
        return true
    }

}
