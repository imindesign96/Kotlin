package com.example.myapp

import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapp.admin.FragmentSales
import com.example.myapp.admin.home.HomeFragment
import com.example.myapp.admin.total.FragmentSimTotal
import com.example.myapp.admin.users.FragmentUsers
import com.example.myapp.databinding.ActivityMainBinding

import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var signOutBtn: Button
    private lateinit var navController: NavController

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.frame, fragment)
                .addToBackStack(null)
            commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.bottomNavigationViewMain.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navi_home ->  replaceFragment(HomeFragment())
                R.id.navi_smile -> replaceFragment(FragmentSimTotal())
                R.id.navi_pets -> replaceFragment(FragmentSales())
                R.id.navi_sun -> replaceFragment(FragmentUsers())
            }
            true
        }


        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController
        // Make sure actions in the ActionBar get propagated to the NavController
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.logomobile)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        setupActionBarWithNavController(navController)
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
